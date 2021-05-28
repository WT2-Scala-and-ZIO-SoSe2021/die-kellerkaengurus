package exercise2

import scala.util.Try

trait QueueLike[T] {
  def enqueue(elem: T): QueueLike[T]

  def dequeue(): Try[QueueLike[T]]

  def front(): Option[T]

  def isEmpty: Boolean
}
