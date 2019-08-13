import Dependencies._

//ThisBuild / scalaVersion     := "2.13.0"
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
      "org.typelevel" %% "cats-effect"  % "2.0.0-RC1",
      "org.typelevel" %% "cats-core"    % "2.0.0-RC1",

      "com.persist"   %% "persist-json" % "1.2.0",

      scalaTest % Test)
  )
