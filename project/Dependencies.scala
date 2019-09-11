import sbt._

trait Versions {

  protected lazy val catsVersion = "2.0.0"

  protected lazy val circeVersion = "0.11.1"

}

object Dependencies extends Versions {

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser").map(_ % circeVersion)

  lazy val cats = Seq(
    "org.typelevel" %% "cats-effect",
    "org.typelevel" %% "cats-core").map(_ % catsVersion)

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"

}
