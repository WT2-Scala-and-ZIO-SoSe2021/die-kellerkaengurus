package exercise4.robots

import exercise4.{CompletedJobsHub, MyEnv, News, Robot}
import zio.{Has, Schedule, ZIO}

class Praiser(val name: String = "Praiser") extends Robot {
  val executeTask: ZIO[Has[News] with Has[CompletedJobsHub], Nothing, Unit] = for {
    sub <- CompletedJobsHub.subscribe()
    job <- sub.use(q => q.take)
    _ <- News.post(s"Good job, ${job.completedBy.name}!")
  } yield ()

  override def work: ZIO[MyEnv, Any, Unit] = for {
    _ <- executeTask repeat Schedule.forever
  } yield ()
}
