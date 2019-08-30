package com.rbobillo.turing.description

import com.rbobillo.turing.io.Color

final case class Description(name:          Name,
                             alphabet:      Alphabet,
                             blank:         Blank,
                             machineStates: MachineStates,
                             initial:       Initial,
                             finals:        Finals,
                             transitions:   Transitions) {

  private def formatProgramName(name: Name): String =
    "* " +
      "".padTo(23 - (name.value.length / 2 + name.value.length % 2), " ").mkString +
      name +
      "".padTo(23 - (name.value.length / 2), " ").mkString +
      " *"

  override def toString: String =
    s"""|**************************************************
        |${formatProgramName(name)}
        |**************************************************
        |
        |${Color cyan "Alphabet"}:      $alphabet
        |${Color cyan "Blank"}:         '$blank'
        |${Color cyan "States"}:        $machineStates
        |${Color cyan "Initial"}:       "$initial"
        |${Color cyan "Finals"}:        $finals
        |${Color cyan "Transitions"}:   $transitions
        |
        |**************************************************
        |""".stripMargin

}
