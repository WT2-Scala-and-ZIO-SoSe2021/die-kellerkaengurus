package exercise4

import zio.{App, ExitCode, URIO, ZIO}
import exercise4.robots.{Elder, Overseer, Praiser, Reporter, Worker}
import zio.clock.Clock
import zio.duration.Duration

/**
 * Build a day in AutoZion as your ZIO Program. Build in a small delay before starting
 * the Elders and start the Reporter last synchronously(for simplicity).
 * Make sure to have at least 2 Workers and Elders and at least one of each other type.
 */
class AutoZion extends App {
  def work(): ZIO[MyEnv, Any, Unit] = {
    val elder1 = new Elder("Justus Jonas")
    val elder2 = new Elder("Ronny")
    val worker1 = new Worker("Kevin")
    val worker2 = new Worker("Abdullah, der Grausame, çüs")
    val overseer = new Overseer("Bob")
    val praiser = new Praiser("Dennis")
    val reporter = new Reporter("Chantal")

    for {
      _ <- worker1.work.fork
      _ <- worker2.work.fork
      _ <- overseer.work.fork
      _ <- praiser.work.fork
      _ <- ZIO.sleep(Duration.fromMillis(100))
      _ <- elder1.work.fork
      _ <- elder2.work.fork
      _ <- reporter.work
    } yield ()
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    val layers = JobBoardLive.layer ++ NewsLive.layer ++ CompletedJobsHubLive.layer
    work().provideCustomLayer(layers).exitCode
  }
}
