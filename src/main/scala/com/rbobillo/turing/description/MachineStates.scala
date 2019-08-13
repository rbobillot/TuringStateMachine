package com.rbobillo.turing.description

case class MachineState(value: String) {
  override def toString: String = "\u001b[33m"+value+"\u001b[0m"
}

case class Initial(state: MachineState) {
  override def toString: String = state.toString
}

case class Finals(states: List[MachineState]) {
  override def toString: String =
    if (states.isEmpty) "[ ]" else states.mkString("[ \"", "\", \"", "\" ]")
}

case class MachineStates(states: List[MachineState]) {
  override def toString: String =
    if (states.isEmpty) "[ ]" else states.mkString("[ \"", "\", \"", "\" ]")

}
