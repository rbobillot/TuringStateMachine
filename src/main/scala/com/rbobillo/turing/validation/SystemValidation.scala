package com.rbobillo.turing.validation

sealed trait SystemValidation extends DomainValidation {
  override val domain: String = "\u001b[91mSystem error\u001b[0m:"
  def errorMessage: String
}

case object CannotOpenFile extends SystemValidation {
  def errorMessage: String = s"$domain Cannot open file, make sure it exists or you have correct access rights."
}