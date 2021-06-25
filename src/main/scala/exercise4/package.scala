import zio.{Has, URIO}
import zio.clock.Clock
import zio.console.Console

import java.time.Duration

package object exercise4 {
  trait Robot {
    val name: String

    def work: URIO[Has[JobBoard] with Has[CompletedJobsHub] with Has[News] with Clock with Console, Unit]
  }

  sealed trait Job {
    val name: String
  }

  case class PendingJob(name: String, duration: Duration) extends Job

  case class CompletedJob(name: String, completedBy: Robot) extends Job
}
