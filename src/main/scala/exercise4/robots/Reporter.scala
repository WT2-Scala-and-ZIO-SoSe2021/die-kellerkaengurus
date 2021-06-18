package exercise4.robots

import exercise4.{MyEnv, News, Robot}
import zio.{Schedule, ZIO, Has}
import zio.console.putStrLn

class Reporter(val name: String = "Reporter") extends Robot {
  val executeTask: ZIO[Has[News], Nothing, Unit] = for {
    news <- News.proclaim()
    _ = putStrLn(news)
  } yield ()

  override def work: ZIO[MyEnv, Any, Unit] = for {
    _ <- executeTask repeat Schedule.forever
  } yield ()
}
