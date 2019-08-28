package com.rbobillo.turing.tape

import com.rbobillo.turing.io.Color.{focused, unfocused}

object ZipperImplicits {

  implicit class Stringifier(zipper: Zipper[String]) {
    def stringify(size: Int): String =
      Cursor.coBind(zipper) { case Zipper(l, c, r) =>
        l.map(unfocused).takeRight(size - 1) #::: focused(c) #:: r.map(unfocused).take(size - 1)
      }.current.take(size).mkString
  }

}
