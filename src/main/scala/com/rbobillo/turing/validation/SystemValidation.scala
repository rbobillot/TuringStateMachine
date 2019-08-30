package com.rbobillo.turing.validation

import com.rbobillo.turing.io.Color

sealed trait SystemValidation extends DomainValidation {
  override val domain: String = Color.red("System error") + ":"
  def errorMessage: String
}

case object CannotOpenFile extends SystemValidation {
  def errorMessage: String = s"$domain Cannot open file, make sure it exists or you have correct access rights."
}