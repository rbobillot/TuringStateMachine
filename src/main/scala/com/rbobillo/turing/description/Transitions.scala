package com.rbobillo.turing.description

case class Step(read:    Character,
                toState: MachineState,
                write:   Character,
                action:  Actions.Value,
                max:     Int = 0) {
  override def toString: String =
    s"($toState, ${
      "".padTo(max - toState.value.length, " ").mkString
    }$write, \u001b[32m${action.toString}\u001b[0m)"
}

case class Transition(state: MachineState,
                      steps: List[Step],
                      max:   Int = 0) {
  override def toString: String =
    steps map (step => s"($state, ${
      "".padTo(max - state.value.length, " ").mkString
    }${step.read}) -> $step") mkString "\n  "
}

case class Transitions(transitions: List[Transition]) {
  override def toString: String =
    if (transitions.isEmpty) "[ ]" else transitions.mkString("[\n  ", ",\n  ", "\n]")
}
