package com.rbobillo.turing.tape

import com.rbobillo.turing.description.Actions

case class Zipper[A](left: Stream[A], current: A, right: Stream[A]) {
  def moveRight: Zipper[A] = copy(
    left    = left #::: Stream(current),
    current = right.headOption.getOrElse(current),
    right   = right drop 1) // safe tail

  def moveLeft: Zipper[A] = copy(
    left    = left.dropRight(1),
    current = left.lastOption.getOrElse(current),
    right   = current #:: right)

  def move(direction: Actions.Value): Zipper[A] =
    if (direction == Actions.RIGHT) moveRight else moveLeft
}

object Zipper {

  def fromString(s: Iterable[Char])(implicit ss: Array[String] = s.mkString split ""): Zipper[String] =
    Zipper(Stream.empty[String], ss.head, Stream.concat(ss.tail))

}