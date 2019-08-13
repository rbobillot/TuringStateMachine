package com.rbobillo.turing.description

case class Name(value: String) {
  override def toString: String = "\u001b[96m"+value+"\u001b[0m"
}