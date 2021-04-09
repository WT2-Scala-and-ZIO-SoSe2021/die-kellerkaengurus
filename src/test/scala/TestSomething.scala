import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{convertToAnyShouldWrapper, equal}

class TestSomething extends AnyFlatSpec{
  "1 plus 1" should "equal 2" in {
    1 + 1 should equal (2)
  }
}
