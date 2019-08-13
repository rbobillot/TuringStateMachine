package com.rbobillo.turing.validation

trait DomainValidation {
  val domain: String = "Domain error: "
  def errorMessage: String
}