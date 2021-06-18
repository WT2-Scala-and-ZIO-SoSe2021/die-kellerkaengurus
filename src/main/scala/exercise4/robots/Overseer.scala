package exercise4.robots
import exercise4.{CompletedJobsHub, MyEnv, News, Robot}
import zio.{Has, Schedule, ZIO}

class Overseer(val name: String = "Overseer") extends Robot {
  val executeTask: ZIO[Has[News] with Has[CompletedJobsHub], Nothing, Unit] = for {
    sub <- CompletedJobsHub.subscribe()
    job <- sub.use(q => q.take)
    _ <- News.post(s"${job.completedBy.name} completed ${job.name}, yayyy")
  } yield ()

  override def work: ZIO[MyEnv, Any, Unit] = for {
    _ <- executeTask repeat Schedule.forever
  } yield ()
}
