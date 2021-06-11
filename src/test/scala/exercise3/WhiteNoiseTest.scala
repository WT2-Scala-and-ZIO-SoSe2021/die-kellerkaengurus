package exercise3

import exercise2.QueueLike
import zio.test._
import zio.test.Assertion._
import zio.test.environment._

import scala.annotation.tailrec

object WhiteNoiseTest extends DefaultRunnableSpec {
  @tailrec
  def checkVolume(queue: QueueLike[Double], volume: Double): Boolean = {
    if (queue.isEmpty)
      return true

    val front = queue.front().get
    if (front < -0.5 * volume || front > 0.5 * volume)
      return false

    val newQueue = queue.dequeue()
    checkVolume(newQueue.get, volume)
  }

  @tailrec
  def countQueueElements(queue: QueueLike[Double], counter: Int = 0): Int = {
    if (queue.isEmpty)
      return counter
    val newQueue = queue.dequeue()
    countQueueElements(newQueue.get, counter + 1)
  }

  override def spec: Spec[TestEnvironment, TestFailure[Nothing], TestSuccess] = suite("White Noise Tests")(
    testM("creates a queue of values between -0.5 and 0.5 for volume = 1") {
      for {
        whiteNoise <- ZioMain.whiteNoise()
        result = checkVolume(whiteNoise, 1)
      } yield assert(result)(isTrue)
    },
    testM("creates a queue of values between -0.5 and 0.5 scaled by the volume") {
      val volume = 0.8
      for {
        whiteNoise <- ZioMain.whiteNoise(volume = volume)
        result = checkVolume(whiteNoise, volume)
      } yield assert(result)(isTrue)
    },
    testM("creates a queue containing <frequency> values") {
      val frequency = 200
      for {
        whiteNoise <- ZioMain.whiteNoise(frequency)
        counter = countQueueElements(whiteNoise)
      } yield assert(counter)(equalTo(frequency))
    }
  )
}
