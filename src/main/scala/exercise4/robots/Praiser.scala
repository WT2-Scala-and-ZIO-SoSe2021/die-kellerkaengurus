package exercise4.robots

import exercise4.{CompletedJobsHub, MyEnv, News, Robot}
import zio.{Has, Schedule, ZIO, ZManaged}

class Praiser(val name: String = "Praiser") extends Robot {
  val executeTask: ZManaged[Has[News] with Has[CompletedJobsHub], Nothing, Unit] = for {
    queue <- CompletedJobsHub.subscribe()
    job <- queue.take.toManaged_
    _ <- News.post(s"Good job, ${job.completedBy.name}!").toManaged_
  } yield ()

  override def work: ZIO[MyEnv, Any, Unit] = for {
    _ <- executeTask.useForever
  } yield ()
}
