name := """schemaRegistry"""
organization := "com.purbon"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test
libraryDependencies += "com.sksamuel.avro4s" % "avro4s-core_2.12" % "1.7.0"

libraryDependencies := libraryDependencies.value.map(_.excludeAll(ExclusionRule("com.google.code.findbugs", "jsr305")))


// major.minor are in sync with the elasticsearch releases
val elastic4sVersion = "5.4.9"
libraryDependencies ++= Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  // for the tcp client
  "com.sksamuel.elastic4s" %% "elastic4s-tcp" % elastic4sVersion,
  
  // for the http client
  "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion,
    "com.sksamuel.elastic4s" %% "elastic4s-circe" % elastic4sVersion,
  
  // if you want to use reactive streams
  "com.sksamuel.elastic4s" %% "elastic4s-streams" % elastic4sVersion,
  
  // testing
  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test",
  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elastic4sVersion % "test"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.purbon.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.purbon.binders._"

sourceDirectories in (Compile, TwirlKeys.compileTemplates) :=
    (unmanagedSourceDirectories in Compile).value