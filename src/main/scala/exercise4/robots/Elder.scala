package exercise4.robots

import exercise4.{JobBoard, MyEnv, PendingJob, Robot}
import zio.{Schedule, ZIO}

import java.time.Duration

class Elder(val name: String = "Elder") extends Robot {
  val policy: Schedule[Any, Any, Long] = Schedule.fixed(Duration.ofSeconds(2))

  override def work: ZIO[MyEnv, Any, Unit] = for { //JobBoard.submit(PendingJob("Job", Duration.ofSeconds(2))) repeat policy.map(_ => ())
    _ <- JobBoard.submit(PendingJob("Job", Duration.ofSeconds(2))) repeat policy
  } yield ()
}
