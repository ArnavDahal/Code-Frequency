name := "CS 474 Final Code"

version := "1.0"

scalaVersion := "2.11.0"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.2"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.12"
libraryDependencies += "org.eclipse.jgit" % "org.eclipse.jgit" % "4.5.0.201609210915-r"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.21"
//libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.2"