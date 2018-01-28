name := "api"
lazy val services = (project in file("../services")).
  enablePlugins(PlayScala)
lazy val api = (project in file(".")).
  enablePlugins(PlayScala).
  dependsOn(services)

