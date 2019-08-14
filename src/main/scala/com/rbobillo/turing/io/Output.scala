package com.rbobillo.turing.io

import cats.effect.IO
import com.rbobillo.turing.description.Description

object Output {

  def printSteps(step: String, ds: Description, pretty: Boolean): IO[Unit] =
    for {
      _ <- if (pretty) IO { Thread sleep 100 ; println("\u001bc" + ds) } else IO.unit
      _ <- IO(println(step))
    } yield ()

  def help: IO[Unit] =
    IO pure {
      """|usage: ./ft_turing [-h] jsonfile input [-p]
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