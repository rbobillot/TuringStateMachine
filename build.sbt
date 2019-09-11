import Dependencies._

// ThisBuild / scalaVersion     := "2.13.0" // persist-json depends on Scala 2.12 collections
ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.rbobillo"
ThisBuild / organizationName := "rbobillo"

lazy val root = (project in file("."))
  .settings(
    name := "turing-state-machine",
    assemblyJarName in assembly := s"turing-state-machine.jar",
    scalacOptions ++= Seq ("-feature"),
    libraryDependencies ++= Seq(
      cats,
      circe,
      Seq("com.persist" % "persist-json_2.12" % "1.2.0"),
      Seq(scalaTest)
    ).flatten
  )

