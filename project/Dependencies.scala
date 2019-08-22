import sbt._

object Dependencies {

  lazy val slf4j = Seq(
    "org.apache.logging.log4j" % "log4j" % "2.8.2" pomOnly()
  )

  lazy val spark  = Seq(
    "org.apache.spark" %% "spark-core" % "2.4.3",
    "org.apache.spark" %% "spark-sql" % "2.4.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  )
  
  lazy val typeSafeConfig = Seq(
    "com.typesafe" % "config" % "1.3.4"
  )
}

