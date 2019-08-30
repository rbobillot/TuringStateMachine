package com.rbobillo.turing.validation

import com.rbobillo.turing.io.Color

sealed trait InputValidation extends DomainValidation {
  override val domain: String = Color.red("Input error") + ":"
  def errorMessage: String
}

case object InvalidInput extends InputValidation {
  def errorMessage: String = s"$domain Invalid input (cannot find some characters in description's alphabet)."
}