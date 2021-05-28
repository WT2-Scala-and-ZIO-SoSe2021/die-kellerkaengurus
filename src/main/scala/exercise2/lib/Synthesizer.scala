package exercise2.lib

import exercise2.{QueueLike, Queue}

import scala.annotation.tailrec
import scala.util.Random

object Synthesizer {
  val ENERGY_DECAY_FACTOR = 0.996

  /**
   * Karplus-Strong Part 1
   *
   * Create a function def whiteNoise(frequency:Int=440, volume:Double = 1.0): Queue[Double]
   * that returns a queue containing a total of frequency elements of random values between .5 and -.5 multiplied by volume.
   * Frequency must be greater than zero and volume is between 0 and 1.
   * */
  def whiteNoise(frequency: Int = 440, volume: Double = 1.0): QueueLike[Double] = {
    if (frequency <= 0 || volume < 0 || volume > 1) {
      throw new IllegalArgumentException
    }

    @tailrec
    def fillQueue(queue: QueueLike[Double], count: Int): QueueLike[Double] = {
      if (count == 0) return queue

      val newQueue = queue.enqueue(Random.between(-0.5, 0.5) * volume)
      fillQueue(newQueue, count - 1)
    }

    fillQueue(new Queue[Double](), frequency)
  }

  /**
   * Karplus-Strong Part 2
   *
   * Remove the front double in the queue and average it with the next double
   * (hint: use dequeue() and peek()) multiplied by an energy decay factor of 0.996 (we’ll call this entire quantity newDouble).
   * Then, add newDouble to the queue.
   *
   * This function should be pure - it takes a queue and returns a new queue
   * */
  def update(queue: QueueLike[Double]): QueueLike[Double] = {
    val newQueue = queue.dequeue().get
    val newDouble = (queue.front().get + newQueue.front().get) / 2.0 * ENERGY_DECAY_FACTOR
    newQueue.enqueue(newDouble)
  }

  /**
   * Karplus-Strong Part 3
   *
   * Create a curried function called loop that takes a queue and a function f:Double => Unit,
   * which can be used to play audio. It takes the queue, passes it to update,
   * plays the front element of the queue and calls itself indefinitely.
  */
  @tailrec
  final def loop(queue: QueueLike[Double])(f: Double => Unit): Unit = {
    val newQueue = update(queue)
    if (Math.abs(newQueue.front().get) < 0.000001)
      return
    f(newQueue.front().get)
    loop(newQueue)(f)
  }
}
