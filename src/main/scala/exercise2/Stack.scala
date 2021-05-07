package exercise2

import java.util.EmptyStackException
import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

class Stack[T] (elem: Option[T] = None, tail: Option[Stack[T]] = None) extends StackLike[T] {

  override def push(elem: T): StackLike[T] = this.pushOption(Option(elem))

  override def pop(): Try[StackLike[T]] = tail match {
    case Some(tail) => Success(tail)
    case None => Failure(new EmptyStackException)
  }

  override def top(): Option[T] = elem

  override def isEmpty: Boolean = elem.isEmpty && tail.isEmpty

  override def reverse: StackLike[T] = recursiveReverse(new Stack[T])

  private def pushOption(elem: Option[T]): Stack[T] = new Stack[T](elem, Option(this))

  @tailrec
  private def recursiveReverse(stack: Stack[T]): Stack[T] = tail match {
    case Some(tail) => tail.recursiveReverse(stack.pushOption(elem))
    case None => stack
  }
}
