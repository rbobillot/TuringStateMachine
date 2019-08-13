package com.rbobillo.turing.validation

sealed trait InputValidation extends DomainValidation {
  override val domain: String = "\u001b[91mInput error\u001b[0m:"
  def errorMessage: String
}

case object InvalidInput extends InputValidation {
  def errorMessage: String = s"$domain Invalid input (cannot find some characters in description's alphabet)."
}