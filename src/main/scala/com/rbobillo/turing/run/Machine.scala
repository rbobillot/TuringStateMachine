package com.rbobillo.turing.run

import cats.effect.{ExitCode, IO}
import com.rbobillo.turing.description.{Character, Description, MachineState, Step, Transition}
import com.rbobillo.turing.io.Output
import com.rbobillo.turing.tape.{Cursor, Zipper}
import com.rbobillo.turing.tape.ZipperImplicits.Stringifier

case class Machine(ds: Description, input: String, cells: Int, pretty: Boolean = false) {

  private def currentTransition(read: String, ms: MachineState): String = {
    val mx = ds.machineStates.states.map(_.value.length).max
    val ts = ds.transitions.transitions.filter(_.state == ms)
    val ss = ts.flatMap(_.steps.filter(_.read.value == read))
    s"""($ms, ${"".padTo(mx - ms.value.length, " ").mkString}${Character(read)}) -> ${ss.headOption.getOrElse("?")}"""
  }

  private def findNextStep(ms: MachineState, read: Character): Option[Step] =
    ds.transitions.transitions
      .filter(_.state == ms)
      .flatMap(_.steps.filter(_.read == read))
      .headOption
      .orElse { // This error should be handled with description file parsing
        println(s"\u001b[91mState error\u001b[0m: Cannot find next Step for : ${s"($ms, $read)"}")
        None
      }

  private def stepString(zipper: Zipper[String], ms: MachineState): String =
    s"[${zipper stringify cells}] ${currentTransition(Cursor coUnit zipper, ms)}"

  private def compute(zipper: Zipper[String], ms: MachineState): IO[ExitCode] =
    zipper match {
      case _ if ds.finals.states.contains(ms) => IO.pure(ExitCode.Success)
      case z => for {
        ss <- IO.pure(stepString(zipper, ms))
        _  <- Output.printStep(ss, ds, pretty)
        cp <- findNextStep(ms, Character(Cursor coUnit z)).fold(IO.pure(ExitCode.Error)) { ns =>
          compute(zipper.copy(current = ns.write.value).move(ns.action), ns.toState)
        }
      } yield cp
    }

  def exec(initialState: MachineState, n: Int): IO[ExitCode] =
    for {
      _      <- IO(println(ds))
      blank  <- IO.pure(ds.blank.character.value)
      input  <- IO.pure(blank*n + input + blank*n) // input surrounded by n blanks
      zipper <- IO.pure(Zipper.fromString(input))
      init   <- IO.pure(Cursor.coJoin(zipper).right(n-1)) // skip the n first blanks
      run    <- compute(init, initialState)
    } yield run

}
