package com.rbobillo.turing.description

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
        |\u001b[96mAlphabet\u001b[0m:      $alphabet
        |\u001b[96mBlank\u001b[0m:         '$blank'
        |\u001b[96mStates\u001b[0m:        $machineStates
        |\u001b[96mInitial\u001b[0m:       "$initial"
        |\u001b[96mFinals\u001b[0m:        $finals
        |\u001b[96mTransitions\u001b[0m:   $transitions
        |
        |**************************************************
        |""".stripMargin

}
