package exercise2

import scala.util.Try

trait StackLike[T] {
  def push(elem: T): StackLike[T]

  def pop(): Try[StackLike[T]]

  def top(): Option[T]

  def isEmpty: Boolean

  def reverse: StackLike[T]
}
