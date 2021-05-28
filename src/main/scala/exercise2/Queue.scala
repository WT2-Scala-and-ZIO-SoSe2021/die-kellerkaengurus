package exercise2

import java.util.EmptyStackException
import scala.util.{Failure, Success, Try}

class Queue[T](val in: StackLike[T] = new Stack[T](), val out: StackLike[T] = new Stack[T]()) extends QueueLike[T] {
  override def enqueue(elem: T): QueueLike[T] = new Queue(in.push(elem), out)

  override def dequeue(): Try[QueueLike[T]] = {
    if (this.isEmpty) Failure(new EmptyStackException)
    else if (!out.isEmpty) Success(new Queue(in, out.pop().get))
    else Success(new Queue(new Stack[T](), in.reverse.pop().get))
  }

  override def front(): Option[T] = {
    if (this.isEmpty || !out.isEmpty) out.top()
    else new Queue(new Stack[T](), in.reverse).out.top()
  }

  override def isEmpty: Boolean = in.isEmpty && out.isEmpty
}
