package exercise3

import exercise2.QueueLike
import zio.test._
import zio.test.Assertion._
import zio.test.environment._

import scala.annotation.tailrec

object WhiteNoiseTest extends DefaultRunnableSpec {
  override def spec: Spec[TestEnvironment, TestFailure[Nothing], TestSuccess] = suite("White Noise Tests")(
    testM("creates a queue of values between -0.5 and 0.5 for volume = 1") {
      @tailrec
      def dequeue(queue: QueueLike[Double]): Boolean = {
        if (queue.isEmpty)
          return true

        val front = queue.front().get
        if (front < -0.5 || front > 0.5)
          return false

        val newQueue = queue.dequeue()
        dequeue(newQueue.get)
      }

      for {
        whiteNoise <- ZioMain.whiteNoise()
        result = dequeue(whiteNoise)
      } yield assert(result)(isTrue)
    },
    testM("creates a queue containing <frequency> values") {
      val frequency = 200

      @tailrec
      def dequeue(queue: QueueLike[Double], counter: Int): Int = {
        if (queue.isEmpty)
          return counter
        val newQueue = queue.dequeue()
        dequeue(newQueue.get, counter + 1)
      }

      for {
        whiteNoise <- ZioMain.whiteNoise(frequency)
        counter = dequeue(whiteNoise, 0)
      } yield assert(counter)(equalTo(frequency))
    }
  )
}
