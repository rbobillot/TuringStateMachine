package com.rbobillo.turing.description

import com.rbobillo.turing.io.Color

case class Character(value: String) {
  override def toString: String = Color magenta value
}

case class Blank(character: Character) {
  override def toString: String = Color.grey(character.value.toCharArray.mkString)
}

case class Alphabet(characters: List[Character]) {
  override def toString: String =
    if (characters.isEmpty) "[ ]" else characters.mkString("[ '", "', '", "' ]")
}
