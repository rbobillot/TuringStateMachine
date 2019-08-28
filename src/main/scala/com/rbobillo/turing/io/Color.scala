package com.rbobillo.turing.io

object Color {

  val blue: String => String = "\u001b[94m" + _ + "\u001b[0m"
  val cyan: String => String = "\u001b[96m" + _ + "\u001b[0m"
  val green: String => String = "\u001b[92m" + _ + "\u001b[0m"
  val grey: String => String = "\u001b[2m" + _ + "\u001b[0m"
  val magenta: String => String = "\u001b[95m" + _ + "\u001b[0m"
  val red: String => String = "\u001b[91m" + _ + "\u001b[0m"
  val yellow: String => String = "\u001b[93m" + _ + "\u001b[0m"

  val focused: String => String = red
  val unfocused: String => String = c => if (c == ".") grey(c) else c

}
