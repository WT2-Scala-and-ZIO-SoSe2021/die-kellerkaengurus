package exercise2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{an, convertToAnyShouldWrapper, thrownBy}

import java.util.EmptyStackException
import scala.annotation.tailrec

class StackTest extends AnyFlatSpec {

  @tailrec
  private final def checkEqual(stack: StackLike[Int], comparisonStack: StackLike[Int]): Unit = {
    stack.top() shouldEqual comparisonStack.top()

    val newStack = stack.pop()
    val newComparisonStack = comparisonStack.pop()
    if (newStack.isFailure || newComparisonStack.isFailure)
      return

    checkEqual(newStack.get, newComparisonStack.get)
  }

  "push" should "add an element on top of the stack" in {
    val stack = new Stack[Int].push(3)
    stack.top() shouldEqual Some(3)
  }

  "pop" should "return the new stack without the top element" in {
    val stack = new Stack[Int].push(3)
    val extendedStack = stack.push(5)

    extendedStack.pop().isFailure shouldBe false
    checkEqual(extendedStack.pop().get, stack)
  }

  it should "return failure with an empty stack exception for an empty stack" in {
    val stack = new Stack[Int]()
    val poppedStack = stack.pop()

    poppedStack.isFailure shouldEqual true
    an [EmptyStackException] shouldBe thrownBy {
      poppedStack.get
    }
  }

  "top" should "return the top element of the stack" in {
    val stack = new Stack[Int]
      .push(3)
      .push(5)
    stack.top() shouldEqual Option(5)
  }

  it should "return None for an empty stack" in {
    val stack = new Stack[Int]
    stack.top() shouldEqual None
  }

  "isEmpty" should "return true for an empty stack" in {
    val stack = new Stack[Int]
    stack.isEmpty shouldEqual true
  }

  it should "return false for a non-empty stack" in {
    val stack = new Stack[Int].push(3)
    stack.isEmpty shouldEqual false
  }

  "reverse" should "reverse the order of the elements in the stack" in {
    val stack = new Stack[Int]
      .push(1)
      .push(2)
      .push(3)

    val reversed = new Stack[Int]
      .push(3)
      .push(2)
      .push(1)

    checkEqual(stack.reverse, reversed)
  }
}
