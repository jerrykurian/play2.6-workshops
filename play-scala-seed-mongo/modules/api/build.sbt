name := "api"
lazy val services = (project in file("../services")).
  enablePlugins(PlayScala)
lazy val document = (project in file("../document")).
  enablePlugins(PlayScala)
lazy val api = (project in file(".")).
  enablePlugins(PlayScala).
  dependsOn(services).dependsOn(document)

