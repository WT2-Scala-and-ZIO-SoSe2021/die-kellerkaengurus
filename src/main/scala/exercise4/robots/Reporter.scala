package exercise4.robots

import exercise4.{News, Robot}
import zio.clock.Clock
import zio.{Has, Schedule, URIO}
import zio.console.{Console, putStrLn}

class Reporter(val name: String = "Reporter") extends Robot {
  val executeTask: URIO[Has[News] with Console, Unit] = for {
    news <- News.proclaim()
    _ <- putStrLn(news).orDie
  } yield ()

  override def work: URIO[Has[News] with Clock with Console, Unit] = for {
    _ <- executeTask repeat Schedule.forever
  } yield ()
}
