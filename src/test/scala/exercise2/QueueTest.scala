package exercise2

import org.scalatest.flatspec.AnyFlatSpec

class QueueTest extends AnyFlatSpec {

  "isEmpty" should "check if the Queue is empty" in {
    new Queue[String]().isEmpty shouldEqual true
  }
}
