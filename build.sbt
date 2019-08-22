import Dependencies._

name := "SparkHomework"

version := "0.1"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Central Repository" 	at 	"http://repo.maven.apache.org/maven2",
  "typesafe" 	at 	"http://repo.typesafe.com/typesafe/repo/"
)

libraryDependencies ++= spark
libraryDependencies ++= slf4j
libraryDependencies ++= typeSafeConfig

fork in run := true
javaOptions in run ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")
outputStrategy := Some(StdoutOutput)

