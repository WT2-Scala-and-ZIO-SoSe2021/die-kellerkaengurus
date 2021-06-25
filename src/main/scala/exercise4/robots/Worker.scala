package exercise4.robots

import exercise4.{CompletedJob, CompletedJobsHub, JobBoard, Robot}
import zio.clock.Clock
import zio.{Has, URIO, ZIO}

class Worker(val name: String = "Worker") extends Robot {
  val executeTask: URIO[Has[CompletedJobsHub] with Clock with Has[JobBoard], Unit] = for {
    job <- JobBoard.take()
    _ <- ZIO.sleep(job.duration)
    _ <- CompletedJobsHub.publish(CompletedJob(job.name, this))
  } yield ()

  override def work: URIO[Has[CompletedJobsHub] with Has[JobBoard] with Clock, Unit] = for {
    _ <- executeTask.forever
  } yield ()
}
