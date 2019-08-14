package com.rbobillo.turing.io

import cats.effect.IO

object Output {

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