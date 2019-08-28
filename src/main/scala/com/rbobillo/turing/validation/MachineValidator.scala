package com.rbobillo.turing.validation

import cats.data.ValidatedNec
import cats.effect.IO
import cats.implicits._
import com.rbobillo.turing.io.Input
import com.rbobillo.turing.description._

sealed trait MachineValidator {

  type Result[T] = ValidatedNec[DomainValidation, T]

  private def validateName(nameO: Option[String]): Result[Name] =
    if (nameO.mkString.nonEmpty) Name(nameO.mkString).validNec
    else NameIsEmpty.invalidNec

  private def validateAlphabet(alphabetO: Option[List[String]]): Result[Alphabet] =
    if (alphabetO.forall(_.forall(_.length == 1))) Alphabet(alphabetO getOrElse Nil map Character).validNec
    else AlphabetIncludesInvalidTokens.invalidNec

  private def validateBlankSize(blankO: Option[String]): Result[Blank] =
    if (blankO.forall(_.length == 1)) Blank(Character(blankO.mkString)).validNec
    else BlankIsNotAsSingleChar.invalidNec

  private def validateBlank(blankO: Option[String], alphabet: Alphabet): Result[Blank] =
    if (blankO.map(Character).forall(alphabet.characters.contains)) Blank(Character(blankO.mkString)).validNec
    else BlankIsNotInAlphabet.invalidNec

  private def validateEachMachineStates(machineStatesO: Option[List[String]]): Result[MachineStates] =
    if (machineStatesO.forall(_.forall(_.nonEmpty))) MachineStates(machineStatesO getOrElse Nil map MachineState).validNec
    else MachineStatesHasEmptyElement.invalidNec

  private def validateMachineStates(machineStatesO: Option[List[String]]): Result[MachineStates] =
    if (machineStatesO.forall(_.nonEmpty)) MachineStates(machineStatesO getOrElse Nil map MachineState).validNec
    else MachineStatesIsEmpty.invalidNec

  private def validateInitial(initialO: Option[String], machineStates: MachineStates): Result[Initial] =
    if (initialO.forall(state => machineStates.states.contains(MachineState(state))))
      Initial(MachineState(initialO.mkString)).validNec
    else
      InitialIsNotInStates.invalidNec

  private def validateFinalsSize(finalsO: Option[List[String]]): Result[Finals] =
    if (finalsO.forall(_.nonEmpty)) Finals(finalsO getOrElse Nil map MachineState).validNec
    else FinalsIsEmpty.invalidNec

  private def validateFinals(finalsO: Option[List[String]], states: MachineStates): Result[Finals] =
    if (finalsO.forall(_.forall(state => states.states.contains(MachineState(state)))))
      Finals(finalsO getOrElse Nil map MachineState).validNec
    else
      FinalsAreNotInStates.invalidNec

  private def validateTransitions(transitionsO: Option[Map[String, List[Map[String, String]]]], machineStates: MachineStates): Result[Transitions] = {
    val maxStateSize = machineStates.states.map(_.value.length).max
    if (transitionsO.forall(_.keys.forall(state => machineStates.states.contains(MachineState(state)))))
      Transitions(transitionsO map (_.toList) getOrElse Nil map { case (s, ss) =>
        Transition(MachineState(s), ss.map { step =>
          Step(
            read    = Character(step("read")),
            toState = MachineState(step("to_state")),
            write   = Character(step("write")),
            action  = if (step("action") == Actions.RIGHT.toString) Actions.RIGHT else Actions.LEFT,
            max     = maxStateSize)
        }, maxStateSize)
      }).validNec
    else TransitionsIncludesInvalidTokens.invalidNec
  }

  private def find[V: Manifest](description: Map[String, Any], k: String): Option[V] =
    description.get(k).collect { case v: V => v }

  private def validateDescription(description: Map[String, Any], input: String): Result[(String, Description)] = {
    type TransMap = Map[String, List[Map[String, String]]]

    val vn = validateName(find[String](description, "name"))
    val va = validateAlphabet(find[List[String]](description, "alphabet"))
    val _b = validateBlankSize(find[String](description, "blank"))
    val vb = validateBlank(find[String](description, "blank"), va getOrElse Alphabet(Nil))
    val _s = validateEachMachineStates(find[List[String]](description, "states"))
    val vs = validateMachineStates(find[List[String]](description, "states"))
    val vi = validateInitial(find[String](description, "initial"), vs getOrElse MachineStates(Nil))
    val _f = validateFinalsSize(find[List[String]](description, "finals"))
    val vf = validateFinals(find[List[String]](description, "finals"), vs getOrElse MachineStates(Nil))
    val vt = validateTransitions(find[TransMap](description, "transitions"), vs getOrElse MachineStates(Nil))

    (vn, va, _b, vb, _s, vs, vi, _f, vf, vt).mapN { (name, alphabet, _, blank, _, states, initial, _, finals, transitions) =>
      input -> Description(name, alphabet, blank, states, initial, finals, transitions)
    }
  }

  def validateMachine(path: String, input: String): IO[Result[(String, Description)]] =
    Input.readDescription(path).attempt.map {
      case Left(_) => CannotOpenFile.invalidNec
      case Right(d) =>
        validateDescription(Input parseJson d, input).fold(
          err => err.invalid,
          res =>
            if (res._1.forall(res._2.alphabet.characters.flatMap(_.value.headOption).contains))
              (res._1.mkString -> res._2).validNec
            else
              InvalidInput.invalidNec
        )
    }
}

object MachineValidator extends MachineValidator
