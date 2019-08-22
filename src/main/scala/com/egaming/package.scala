package com

package object egaming {

  case class Bets(payout: Double, stake: Double, code: String)
  case class Participants(playerId: String, playerCurrency: String, bets: List[Bets])
  case class GameData(created: String, participants: List[Participants])
  case class Game(gameData: GameData, id: String)


  case class BetsWithDiff(payout: Double, stake: Double, code: String, diff:Double)
  case class ParticipantsWithDiff(playerId: String, playerCurrency: String, bets: List[BetsWithDiff])
  case class GameDataWithDiff(created: String, participants: List[ParticipantsWithDiff])
  case class GameWithDiff(gameData: GameDataWithDiff, id: String)

  case class CSVResult(gameID: String, stake_payout_diff: String)

}
