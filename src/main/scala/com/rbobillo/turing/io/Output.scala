package com.rbobillo.turing.io

import cats.effect.IO
import com.rbobillo.turing.description.{Character, Description, MachineState}
import com.rbobillo.turing.tape.{Cursor, Zipper}
import com.rbobillo.turing.tape.ZipperImplicits.Stringifier

object Output {

  private def formatTransition(read: String, ms: MachineState, ds: Description): IO[String] =
    for {
      mx <- IO.pure(ds.machineStates.states.map(_.value.length).max)
      ts <- IO.pure(ds.transitions.transitions.filter(_.state == ms))
      ss <- IO.pure(ts.flatMap(_.steps.filter(_.read.value == read)).headOption.getOrElse("?"))
      ch <- IO.pure("".padTo(mx - ms.value.length, " ").mkString + Character(read))
    } yield
      s"""($ms, $ch) -> $ss"""

  def formatStep(zipper: Zipper[String], ms: MachineState, cellsQty: Int, ds: Description): IO[String] =
    for {
      cells <- IO.pure(zipper stringify cellsQty)
      trans <- formatTransition(Cursor coUnit zipper, ms, ds)
    } yield
      s"[$cells] $trans"

  def printStep(step: String, ds: Description, pretty: Boolean): IO[Unit] =
    for {
      _ <- if (pretty) IO { Thread sleep 100 ; println(Color.clearScreen + ds) } else IO.unit
      _ <- IO(println(step))
    } yield ()

  def help: IO[Unit] =
    IO pure {
      """|usage: ./turing [-h] jsonfile input [-p]
         |
         |positional arguments:
         |  jsonfile            json description of the machine
         |
         |  input               input of the machine
         |
         |optional arguments:
         |  -h, --help          show this help message and exit
         |  -p                  activates pretty print""".stripMargin
    } map println

}