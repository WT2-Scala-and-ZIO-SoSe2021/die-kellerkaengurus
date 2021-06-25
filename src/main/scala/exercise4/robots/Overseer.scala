package exercise4.robots
import exercise4.{CompletedJobsHub, News, Robot}
import zio.{Has, URIO}

class Overseer(val name: String = "Overseer") extends Robot {
  val executeTask: URIO[Has[News] with Has[CompletedJobsHub], Unit] = CompletedJobsHub.subscribe().use(q =>
      q.take.flatMap(job => News.post(s"${job.completedBy.name} completed ${job.name}, yayyy")).forever
    )

  override def work: URIO[Has[News] with Has[CompletedJobsHub], Unit] = for {
    _ <- executeTask
  } yield ()
}
