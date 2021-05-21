import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class Exercise1 extends AnyFlatSpec {
  val array: Array[Int] = Array(1, 3, 6, 8, 2)
  val optimisticHand: Array[Int] = Array(2, 2, 11, 3, 2)
  val pessimisticHand: Array[Int] = Array(2, 2, 11, 3, 4)
  val optionalHand: Array[Int] = Array(11, 11, 9)

  /* Task 1 */
  "max" should "return the biggest number in the list" in {
    Main.max(array).get shouldEqual 8
  }

  it should "return empty optional for empty arrays" in {
    Main.max(Array()) shouldEqual None
  }

  "min" should "return the lowest number in the array" in {
    Main.min(array).get shouldEqual 1
  }

  it should "return None for empty arrays" in {
    Main.min(Array()) shouldEqual None
  }

  "sum" should "sum up all elements in the array" in {
    Main.sum(array) shouldEqual 20
  }

  it should "return 0 for empty arrays" in {
    Main.sum(Array()) shouldEqual 0
  }

  /* Task 2A */
  "parse" should "return an int" in {
    Main.parse("A") shouldEqual Some(11)
  }

  it should "return 10 for J, Q, K" in {
    Main.parse("J") shouldEqual Some(10)
    Main.parse("Q") shouldEqual Some(10)
    Main.parse("K") shouldEqual Some(10)
  }

  it should "return the integer values for strings from 2-10" in {
    Main.parse("2") shouldBe Some(2)
    Main.parse("5") shouldBe Some(5)
    Main.parse("10") shouldBe Some(10)
  }

  it should "return None for invalid strings" in {
    Main.parse("1") shouldBe None
  }

  "parseAll" should "return Array(2, 10) for cards 2 and K" in {
    Main.parseAll(Array("2", "K")) shouldBe Array(2, 10)
  }

  it should "return an empty array for an empty card array" in {
    Main.parseAll(Array()) shouldBe Array()
  }

  /* Task 2B */
  "values" should "return the possible values of a card" in {
    Main.values(8) shouldBe Array(8)
  }

  it should "return [1,11]" in {
    Main.values(11) shouldBe Array(1, 11)
  }

  /* Task 2C */
  "determineHandValue" should "return the hand value" in {
    val testHand = Array(4, 11, 10)
    Main.determineHandValue(Main.max)(testHand) shouldEqual 25
    Main.determineHandValue(Main.min)(testHand) shouldEqual 15
  }

  /* Task 2D */
  "optimisticF" should "return 12 for cards array (2,11)" in {
    Main.optimisticF(Array(2, 11)) shouldBe 13
  }

  it should "return 2 for cards array (2)" in {
    Main.optimisticF(Array(2)) shouldBe 2
  }

  "pessimisticF" should "return 1 for cards array (2,11)" in {
    Main.pessimisticF(Array(2, 11)) shouldBe 3
  }

  it should "return 2 for cards array (2)" in {
    Main.pessimisticF(Array(2)) shouldBe 2
  }

  /* Task 2E */
  "determineBestHandValue" should "return 20 for card array [2, 2, 11, 3, 2]" in {
    Main.determineBestHandValue(optimisticHand) shouldBe 20
  }

  it should "return 12 for card array [2, 2, 11, 3, 4]" in {
    Main.determineBestHandValue(pessimisticHand) shouldBe 12
  }

  /* Optional Task */
  "determineBestHandValue" should "return 21 for card array [11, 11, 9]" in {
    Main.determineBestHandValue(optionalHand) shouldBe 21
  }
}
