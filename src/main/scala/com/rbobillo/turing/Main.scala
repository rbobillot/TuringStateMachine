package com.rbobillo.turing

import cats.data.NonEmptyChain
import cats.data.Validated.{Invalid, Valid}
import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._

import com.rbobillo.turing.io.Output
import com.rbobillo.turing.run.Machine
import com.rbobillo.turing.validation.{DomainValidation, MachineValidator}

object Main extends IOApp {

  private def displayDescriptionErrors(err: NonEmptyChain[DomainValidation]): IO[Unit] =
    IO(err.toList.map(_.errorMessage) foreach println)

  private def bootTuringMachine(descriptionPath: String, input: String): IO[ExitCode] =
    MachineValidator.validateMachine(descriptionPath, input).flatMap {
      case Valid((_, d)) => Machine.exec(d, input.mkString("...","","..."))
      case Invalid(err)  => displayDescriptionErrors(err).as(ExitCode.Error)
    }

  def run(args: List[String]): IO[ExitCode] =
    args match {
      case xs if xs exists Set("-h","--help") => Output.help.as(ExitCode.Success)
      case jsonFile :: input :: Nil           => bootTuringMachine(jsonFile, input)
      case _                                  => Output.help.as(ExitCode.Success)
    }

}
