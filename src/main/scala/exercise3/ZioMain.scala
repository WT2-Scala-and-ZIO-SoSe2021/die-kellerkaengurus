package exercise3

import exercise2.lib.StdAudio
import exercise2.{Queue, QueueLike}
import zio.{ExitCode, RIO, UIO, URIO, ZIO}

import zio.console._
import zio.random._
import zio.stream._

object ZioMain extends zio.App {

  val ENERGY_DECAY_FACTOR = 0.996

  override def run(args: List[String]): URIO[Random with Console, ExitCode] = program.exitCode

  val program: RIO[Random, Unit] = {
    for {
      whiteNoise <- whiteNoise()
      _ <- loop(whiteNoise)
    } yield ()
  }

  def play(sample: Double): UIO[Unit] = ZIO.succeed(StdAudio.play(sample))

  /**
   * Karplus-Strong Part 1.2
   *
   * Create a function def whiteNoise(frequency:Int=440, volume:Double = 1.0): Queue[Double]
   * that returns a queue containing a total of frequency elements of random values between .5 and -.5 multiplied by volume.
   * Frequency must be greater than zero and volume is between 0 and 1.
   *
   * Modify the whiteNoise method, so it uses zio.random for the generation and returns a URIO of Random and Queue[Double].
   *
   * mapM: https://zio.dev/docs/datatypes/concurrency/queue#zqueuemapm
   * Sink: https://zio.dev/docs/datatypes/stream/sink
   * */
  def whiteNoise(frequency: Int = 440, volume: Double = 1.0): URIO[Random, Queue[Double]] = {
    Stream.fromIterable(1 to frequency)
      .mapM(_ => nextDoubleBetween(-0.5, 0.5)
        .map(d => d * volume)
      )
      .run(Sink.foldLeft(new Queue[Double]())((q, e) => q.enqueue(e).asInstanceOf[Queue[Double]]))
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
   * Karplus-Strong Part 3.2
   *
   * Create a curried function called loop that takes a queue and a function f:Double => Unit,
   * which can be used to play audio. It takes the queue, passes it to update,
   * plays the front element of the queue and calls itself indefinitely.
   *
   * Modify the loop method, so it has a single parameter -
   * the queue and returns a ZIO[Random, Throwable, Unit]. Make sure not to change update,
   * it should still return an Option rather than a ZIO.
   *
   * Lösungsanätze:
   * - URIO übergeben
   */
  final def loop(queue: QueueLike[Double]): RIO[Random, Unit] = {
    for {
      newQueue <- ZIO.succeed(update(queue))
      _ <- play(queue.front().get)
      _ <- loop(newQueue)
    } yield ()
  }
}
