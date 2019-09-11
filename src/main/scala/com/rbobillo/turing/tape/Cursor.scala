package com.rbobillo.turing.tape

object Cursor extends Comonad[Zipper] {

  def map[A,B](wa: Zipper[A])(f: A => B): Zipper[B] =
    wa.copy(
      left    = wa.left.map(f),
      current = f(wa.current),
      right   = wa.right.map(f))

  def coUnit[A](wa: Zipper[A]): A =
    wa.current

  def coJoin[A](wa: Zipper[A]): Zipper[Zipper[A]] =
    wa.copy(
      left    = Stream.iterate(wa.moveLeft)(_.moveLeft).zip(wa.left drop 1).map(_._1),
      current = wa,
      right   = Stream.iterate(wa.moveRight)(_.moveRight).zip(wa.right drop 1).map(_._1))

  def coBind[A,B](wa: Zipper[A])(f: Zipper[A] => B): Zipper[B] =
    map(coJoin(wa))(f)

}