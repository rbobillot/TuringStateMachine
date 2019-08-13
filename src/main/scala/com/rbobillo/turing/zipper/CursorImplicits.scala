package com.rbobillo.turing.zipper

object CursorImplicits {

  implicit class CharZipper(cz: Cursor[String]) {
    def stringify: String = stringify(30)
    def stringify(size: Int, start: String = "[", end: String = "]"): String = {
      val l = cz.left.toList.map(c => if (c == ".") "\u001b[2m.\u001b[0m" else c)
      val c = s"\u001b[91m${cz.current}\u001b[0m"
      val r = cz.right.toList.map(c => if (c == ".") "\u001b[2m.\u001b[0m" else c)

      val chars =
        if (size < 1) l ++ List(c) ++ r
        else (l.takeRight(size - 1) ++ List(c) ++ r.take(size - 1)).take(size)

      start + chars.mkString + end
    }
  }

}
