package com.rbobillo.turing.description

import com.rbobillo.turing.io.Color

case class MachineState(value: String) {
  override def toString: String = Color darkyellow value
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
