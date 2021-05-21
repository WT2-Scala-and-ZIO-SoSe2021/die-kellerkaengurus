package exercise2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{an, convertToAnyShouldWrapper, thrownBy}

import java.util.EmptyStackException

class QueueTest extends AnyFlatSpec {
  "enqueue" should "add an element to the queue" in {
    val queueItem = "Test"
    val queue = new Queue[String]().enqueue(queueItem)
    queue.front() shouldBe Some(queueItem)
  }

  "dequeue" should "return the queue without the first element" in {
    val queue = new Queue[String]().enqueue("First").enqueue("Second").enqueue("Third")
    val newQueue = queue.dequeue().get
    newQueue.front() shouldBe Option("Second")
  }

  it should "return Failure for empty queues" in {
    val queue = new Queue[String]()
    an [EmptyStackException] shouldBe thrownBy {
      queue.dequeue().get
    }
  }

  "front" should "return the front elem of the queue if the queue is not empty" in {
    val queue = new Queue[String]().enqueue("First").enqueue("Second").enqueue("Third")
    queue.front() shouldBe Some("First")
    val q = queue.dequeue().get
    q.front() shouldBe Some("Second")
    val r = q.dequeue().get
    r.front() shouldBe Some("Third")
  }

  it should "return None if the queue is empty" in {
    val queue = new Queue[String](out = new Stack[String]())
    queue.front() shouldBe None
  }

  "isEmpty" should "check if the Queue is empty" in {
    new Queue[String]().isEmpty shouldEqual true
  }
}
