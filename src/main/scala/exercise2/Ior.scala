package exercise2

sealed trait Ior[A] {
  // Remember that flatMap is right biased, so it only works on A.
  // Hint: you will probably need nested pattern matching for this
  def flatMap[B](f: A => Ior[B]): Ior[B] = this match {
    case Left(elem) => Left(elem)
    case Right(elem) => f(elem)
    case Both(left, elem) => f(elem) match {
      case Left(newElem) => Left(newElem)
      case Right(newElem) => Both(left, newElem)
      case Both(newLeft, newElem) => Both(newLeft, newElem)
    }
  }

  def map[B](f: A => B): Ior[B] = flatMap(elem => Ior.unit(f(elem)))
}

case class Left[A](elem: Throwable) extends Ior[A]

case class Right[A](elem: A) extends Ior[A]

case class Both[A](left: Throwable, elem: A) extends Ior[A]

object Ior {
  def left[A](elem: Throwable): Left[A] = Left(elem)

  def right[A](elem: A): Right[A] = Right(elem)

  def both[A](left: Throwable, elem: A): Both[A] = Both(left, elem)

  // Create a unit method in the companion object. It should take an A and return Ior[A]
  def unit[A](elem: A): Ior[A] = Right(elem)
}
