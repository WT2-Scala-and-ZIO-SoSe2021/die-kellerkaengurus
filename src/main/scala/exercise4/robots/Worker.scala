package exercise4.robots

import exercise4.{CompletedJob, CompletedJobsHub, JobBoard, MyEnv, Robot}
import zio.clock.Clock
import zio.{Has, Schedule, ZIO}

class Worker(val name: String = "Worker") extends Robot {
  val executeTask: ZIO[Has[CompletedJobsHub] with Clock with Has[JobBoard], Nothing, Unit] = for {
    job <- JobBoard.take()
    _ <- ZIO.sleep(job.duration)
    _ <- CompletedJobsHub.publish(CompletedJob(job.name, this))
  } yield ()

  override def work: ZIO[MyEnv, Any, Unit] = for {
    _ <- executeTask.forever
  } yield ()
}
