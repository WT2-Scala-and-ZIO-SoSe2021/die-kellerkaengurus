package exercise2

sealed trait Ior[E <: Throwable, A] {
  // Remember that flatMap is right biased, so it only works on A.
  // Hint: you will probably need nested pattern matching for this
  def flatMap[E1 >: E <: Throwable, B](f: A => Ior[E1, B]): Ior[E1, B] = this match {
    case Left(elem) => Left(elem)
    case Right(elem) => f(elem)
    case Both(left, elem) => f(elem) match {
      case Left(newElem) => Left(newElem)
      case Right(newElem) => Both(left, newElem)
      case Both(newLeft, newElem) => Both(newLeft, newElem)
    }
  }

  def map[E1 >: E <: Throwable, B](f: A => B): Ior[E1, B] = flatMap(elem => Ior.unit(f(elem)))
}

case class Left[E <: Throwable, A](elem: Throwable) extends Ior[E, A]

case class Right[E <: Throwable, A](elem: A) extends Ior[E, A]

case class Both[E <: Throwable, A](left: Throwable, elem: A) extends Ior[E, A]

object Ior {
  def left[E <: Throwable, A](elem: Throwable): Left[E, A] = Left(elem)

  def right[E <: Throwable, A](elem: A): Right[E, A] = Right(elem)

  def both[E <: Throwable, A](left: Throwable, elem: A): Both[E, A] = Both(left, elem)

  // Create a unit method in the companion object. It should take an A and return Ior[A]
  def unit[E <: Throwable, A](elem: A): Ior[E, A] = Right(elem)
}
