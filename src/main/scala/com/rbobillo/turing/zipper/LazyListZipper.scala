package com.rbobillo.turing.zipper

object LazyListZipper extends Comonad[Cursor] {

  def map[A,B](wa: Cursor[A])(f: A => B): Cursor[B] =
    wa.copy(
      left    = wa.left.map(f),
      current = f(wa.current),
      right   = wa.right.map(f))

  def coUnit[A](wa: Cursor[A]): A =
    wa.current

  def coJoin[A](wa: Cursor[A]): Cursor[Cursor[A]] =
    wa.copy(
      left    = Stream.iterate(wa.moveLeft)(_.moveLeft).zip(wa.left.tail).map(_._1),
      current = wa,
      right   = Stream.iterate(wa.moveRight)(_.moveRight).zip(wa.right.tail).map(_._1))

  def =>>[A,B](wa: Cursor[A])(f: Cursor[A] => B): Cursor[B] =
    map(coJoin(wa))(f)

}