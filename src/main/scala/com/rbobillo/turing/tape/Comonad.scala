package com.rbobillo.turing.tape

import scala.language.higherKinds

sealed trait Functor[F[_]] {
  def map[A,B](fa: F[A])(f: A => B): F[B]
}

trait Comonad[W[_]] extends Functor[W] {

  def map[A,B](wa: W[A])(f: A => B): W[B]

  def coUnit[A](wa: W[A]): A

  def coJoin[A](wa: W[A]): W[W[A]]

  def coBind[A,B](wa: W[A])(f: W[A] => B): W[B]

}