name := """play-scala-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"
lazy val api = (project in file("modules/api")).
    enablePlugins(PlayScala)
lazy val services = (project in file("modules/services")).
  enablePlugins(PlayScala)
lazy val document = (project in file("modules/document")).
  enablePlugins(PlayScala)
lazy val root = (project in file(".")).
  enablePlugins(PlayScala).
  dependsOn(api).aggregate(api).
  dependsOn(services).aggregate(services).
  dependsOn(document).aggregate(document)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.mockito" % "mockito-all" % "1.8.4"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
