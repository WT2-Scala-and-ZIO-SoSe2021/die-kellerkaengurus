package exercise4

import zio.{Has, Queue, UIO, ULayer, URIO, ZIO, ZLayer}

trait JobBoard {
  /**
  Submits a job to the job board, which can later be taken up by a robot using the take method.
   */
  def submit(job: PendingJob): UIO[Unit]

  /**
  Take a job from the job board
   */
  def take(): UIO[PendingJob]
}

// Module Pattern 2.0

case class JobBoardLive(queue: Queue[PendingJob]) extends JobBoard {
  override def submit(job: PendingJob): UIO[Unit] = for {
    _ <- queue.offer(job)
  } yield ()

  override def take(): UIO[PendingJob] = for {
    job <- queue.take
  } yield job
}

object JobBoard {
  def submit(job: PendingJob): URIO[Has[JobBoard], Unit] = ZIO.serviceWith[JobBoard](_.submit(job))

  def take():URIO[Has[JobBoard], PendingJob] = ZIO.serviceWith[JobBoard](_.take())
}

object JobBoardLive {
  val layer: ULayer[Has[JobBoard]] = ZLayer.fromEffect(Queue.unbounded[PendingJob].map(queue => JobBoardLive(queue)))
}