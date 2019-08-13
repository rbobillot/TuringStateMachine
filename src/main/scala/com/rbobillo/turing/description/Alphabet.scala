package com.rbobillo.turing.description

case class Character(value: String) {
  override def toString: String = "\u001b[95m"+value+"\u001b[0m"
}

case class Blank(character: Character) {
  override def toString: String = "\u001b[2m"+character.value.toCharArray.mkString+"\u001b[0m"
}

case class Alphabet(characters: List[Character]) {
  override def toString: String =
    if (characters.isEmpty) "[ ]" else characters.mkString("[ '", "', '", "' ]")
}
