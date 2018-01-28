name := "api"
lazy val services = (project in file("../services")).
  enablePlugins(PlayScala)
lazy val api = (project in file(".")).
  enablePlugins(PlayScala).
  dependsOn(services)

libraryDependencies += "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5"
