import zio.{Has, ZEnv, ZIO, ZLayer}
import zio.clock.Clock

import java.time.Duration

package object exercise4 {
  type MyEnv = Has[JobBoard] with Has[CompletedJobsHub] with Has[News] with Clock

  trait Robot {
    val name: String

    def work: ZIO[MyEnv, Any, Unit]
  }

  sealed trait Job {
    val name: String
  }

  case class PendingJob(name: String, duration: Duration) extends Job

  case class CompletedJob(name: String, completedBy: Robot) extends Job
}
