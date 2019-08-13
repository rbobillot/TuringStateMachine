package com.rbobillo.turing.zipper

import com.rbobillo.turing.description.Actions

case class Cursor[A](left: Stream[A], current: A, right: Stream[A]) {
  def moveRight: Cursor[A] = copy(
    left    = left #::: Stream(current),
    current = right.headOption.getOrElse(current),
    right   = right.tail)

  def moveLeft: Cursor[A] = copy(
    left    = left.dropRight(1),
    current = left.lastOption.getOrElse(current),
    right   = current #:: right)

  def move(direction: Actions.Value): Cursor[A] =
    if (direction == Actions.RIGHT) moveRight else moveLeft
}

object Cursor {

  def fromString(s: Iterable[Char])(implicit ss: Array[String] = s.mkString split ""): Cursor[String] =
    Cursor(Stream.empty[String], ss.head, Stream.concat(ss.tail))

}