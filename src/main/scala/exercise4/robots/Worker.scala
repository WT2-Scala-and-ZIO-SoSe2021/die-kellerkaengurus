package exercise4.robots

import exercise4.{CompletedJob, CompletedJobsHub, JobBoard, Robot}
import zio.clock.Clock
import zio.{Has, URIO, ZIO}
import zio.random._

import java.time.Duration

class Worker(val name: String = "Worker") extends Robot {
  def withReboot: URIO[Clock, Unit] = ZIO.sleep(Duration.ofSeconds(2))

  def executeTask(): URIO[Has[CompletedJobsHub] with Clock with Has[JobBoard] with Random, Unit] = {
    for {
      job <- JobBoard.take()
      _ <- ZIO.sleep(job.duration)
      _ <- ZIO.ifM(nextIntBetween(0, 5).map(i => i == 0))(
        JobBoard.submit(job).map(_ => withReboot),
        CompletedJobsHub.publish(CompletedJob(job.name, this))
      )
    } yield ()
  }

  override def work: URIO[Has[CompletedJobsHub] with Has[JobBoard] with Clock with Random, Unit] = executeTask().forever
}
