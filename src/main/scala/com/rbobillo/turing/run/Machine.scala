package com.rbobillo.turing.run

import cats.effect.{ExitCode, IO}
import cats.syntax.all._
import com.rbobillo.turing.description.{Character, Description, MachineState, Step, Transition}
import com.rbobillo.turing.io.Output
import com.rbobillo.turing.tape.Cursor
import com.rbobillo.turing.tape.CursorImplicits.CharZipper

case class Machine(pretty: Boolean = false) {

  private def findNextStep(ms: MachineState, read: String, ts: List[Transition]): Option[Step] =
    ts.filter(_.state == ms)
      .flatMap(_.steps.filter(_.read.value == read))
      .headOption
      .orElse { // This error should be handled with description file parsing
        println(s"\u001b[91mState error\u001b[0m: Cannot find next Step for : ${s"($ms, ${Character(read)})"}")
        None
      }

  private def currentTransition(read: String, ms: MachineState, ds: Description): String = {
    val mx = ds.machineStates.states.map(_.value.length).max
    val ts = ds.transitions.transitions.filter(_.state == ms)
    val ss = ts.flatMap(_.steps.filter(_.read.value == read))
    s"($ms, ${"".padTo(mx - ms.value.length, " ").mkString}${Character(read)}) -> ${ss.head}"
  }

  private def nextStep(cursor: Cursor[String], ms: MachineState, ds: Description, sz: Int): String =
    s"${cursor.stringify(sz)} ${currentTransition(cursor.current, ms, ds)}"

  private def readAndUpdateInput(cursor: Cursor[String], inputSize: Int, ms: MachineState, ds: Description): IO[ExitCode] =
    cursor match {
      case Cursor(_, _, _) if ds.finals.states.contains(ms) => IO.pure(ExitCode.Success)
      case Cursor(_, c, _) => for {
        s <- IO.pure(nextStep(cursor, ms, ds, inputSize))
        _ <- Output.printSteps(s, ds, pretty)
        r <- findNextStep(ms, c, ds.transitions.transitions).fold(IO.pure(ExitCode.Error)) { next =>
          readAndUpdateInput(
            cursor.copy(current = next.write.value).move(next.action),
            inputSize,
            next.toState,
            ds)
        }
      } yield r
    }

  def exec(description: Description, input: String): IO[ExitCode] =
    for {
      _ <- IO(println(description))
      r <- readAndUpdateInput(
        Cursor.fromString(input).moveRight.moveRight.moveRight,
        input.length,
        description.initial.state,
        description)
    } yield r

}