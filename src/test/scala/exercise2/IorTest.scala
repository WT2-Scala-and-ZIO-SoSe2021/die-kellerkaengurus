package exercise2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class IorTest extends AnyFlatSpec {
  "map" should "map the Ior element using the passed function" in {
    val result = Ior.right(2).map(x => x * 4)

    result shouldBe Right(8)
  }

  "flatMap" should "map the Ior element using the passed function" in {
    val result = Ior.unit(3).flatMap(_ => Ior.right("a string"))

    result shouldBe Right("a string")
  }
}
