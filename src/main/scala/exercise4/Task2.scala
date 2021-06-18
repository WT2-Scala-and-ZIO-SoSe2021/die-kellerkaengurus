package exercise4

import zio.{ExitCode, URIO, ZIO}

object Task2 extends zio.App {

  val program: ZIO[Any, String, Unit] = {
    for {
      task <- (ZIO.interrupt zipPar ZIO.fail("Error")).fork
      _ <- task.join
    } yield ()
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = program.exitCode
}
