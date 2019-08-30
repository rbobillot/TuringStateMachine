package com.rbobillo.turing.validation

import com.rbobillo.turing.io.Color

sealed trait DescriptionValidation extends DomainValidation {
  override val domain: String = Color.red("Description error") + ":"
  def errorMessage: String
}

case object NameIsEmpty extends DescriptionValidation {
  def errorMessage: String = s"$domain Name must not be empty."
}

case object AlphabetIsEmpty extends DescriptionValidation {
  def errorMessage: String = s"$domain Alphabet must not be empty."
}

case object AlphabetIncludesInvalidTokens extends DescriptionValidation {
  def errorMessage: String = s"$domain Every Alphabet characters must strings of length equal to one."
}

case object BlankIsNotAsSingleChar extends DescriptionValidation {
  def errorMessage: String = s"$domain Blank must be a single char."
}

case object BlankIsNotInAlphabet extends DescriptionValidation {
  def errorMessage: String = s"$domain Blank must be contained in alphabet."
}

case object MachineStatesHasEmptyElement extends DescriptionValidation {
  def errorMessage: String = s"$domain Each State must not be empty."
}

case object MachineStatesIsEmpty extends DescriptionValidation {
  def errorMessage: String = s"$domain States must not be empty."
}

case object InitialIsNotInStates extends DescriptionValidation {
  def errorMessage: String = s"$domain Initial must be contained in states."
}

case object FinalsIsEmpty extends DescriptionValidation {
  def errorMessage: String = s"$domain Every finals must be contained in states."
}

case object FinalsAreNotInStates extends DescriptionValidation {
  def errorMessage: String = s"$domain Every finals must be contained in states."
}

case object TransitionsIncludesInvalidTokens extends DescriptionValidation {
  def errorMessage: String = s"$domain Transitions must contain valid transitions."
}
