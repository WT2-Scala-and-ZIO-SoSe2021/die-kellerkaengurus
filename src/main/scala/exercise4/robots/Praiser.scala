package exercise4.robots

import exercise4.{CompletedJobsHub, News, Robot}
import zio.{Has, URIO, ZManaged}

class Praiser(val name: String = "Praiser") extends Robot {
  val executeTask: URIO[Has[News] with Has[CompletedJobsHub], Unit] = CompletedJobsHub.subscribe().use(queue =>
    queue.take.flatMap(job => News.post(s"Good job, ${job.completedBy.name}!")).forever
  )

  override def work: URIO[Has[News] with Has[CompletedJobsHub], Unit] = for {
    _ <- executeTask
  } yield ()
}
