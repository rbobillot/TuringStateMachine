package com.rbobillo.turing.tape

import com.rbobillo.turing.io.Color.{focused, unfocused}

object ZipperImplicits {

  implicit class Stringifier(cz: Zipper[String]) {
    def stringify: String = stringify(30)
    def stringify(size: Int, start: String = "[", end: String = "]"): String =
      Cursor.coBind(cz) { case Zipper(l, c, r) =>
        l.map(unfocused).takeRight(size - 1) #::: focused(c) #:: r.map(unfocused).take(size - 1)
      }.current.take(size).mkString(start, "", end)

  }

}
