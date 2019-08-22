package com.egaming

import org.apache.spark.sql.functions.unix_timestamp
import org.apache.spark.sql.{Dataset, SaveMode}
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession

object Application extends App{

  // Get config by using typesafe library
  private implicit val conf: Config      = ConfigFactory.load
  private val appName                    = conf.getString("spark.spark.app.name")
  private val master                    = conf.getString("spark.spark.master")

  // Create Spark Session
  val sparkEngine = SparkSession
    .builder
    .appName(appName)
    .master(master)
    .getOrCreate()

  import sparkEngine.implicits._

  // Read json file
  val game = sparkEngine.read.option("multiLine", true).json("src/main/resources/data.json").as[Game]

  // Convert Game type to GameWithDiff type
  val gameWithDiff :Dataset[GameWithDiff]= game.map(data => data match {
    case Game(gameData, id) => new GameWithDiff(gameData match {
      case  GameData(created, participants) => new GameDataWithDiff(created, {
        participants.map(participant => new ParticipantsWithDiff(participant.playerId, participant.playerCurrency,{
          participant match {
            case Participants(_,_,bets) => {
              bets.map(bet => new BetsWithDiff(bet.payout, bet.stake, bet.code, bet.payout - bet.stake))
            }
          }}
        ))
      })
    },id)
  })

  gameWithDiff.show(false)

  // Store it as json with created timestamp
  gameWithDiff.withColumn("created",(unix_timestamp($"gameData.created"))).toDF().coalesce(1).write.partitionBy("created").mode(SaveMode.Overwrite).json("src/main/resources/game")

  val csvResult = sparkEngine.read.json("src/main/resources/game/*/*").as[GameWithDiff].map(data => data match {
    case GameWithDiff(gameData, id) => new CSVResult(id,gameData match {
      case GameDataWithDiff(_, participants) => {
        participants.flatMap(participant => participant.bets.map(bet => (bet.stake, bet.payout, bet.diff))).mkString("[",",","]")
      }
    })
  })

  csvResult.show(false)

  // Store it as CSV
  csvResult.coalesce(1).
    write.option("header", "true").mode(SaveMode.Overwrite).csv("src/main/resources/out.csv")

}
