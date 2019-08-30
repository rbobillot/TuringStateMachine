package com.rbobillo.turing.description

import com.rbobillo.turing.io.Color

case class Name(value: String) {
  override def toString: String = Color cyan value
}