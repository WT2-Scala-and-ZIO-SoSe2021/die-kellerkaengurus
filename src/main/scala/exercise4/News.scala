package exercise4

import zio.{Has, Queue, UIO, ULayer, URIO, ZIO, ZLayer}

trait News {
  def post(news: String): UIO[Unit]

  def proclaim(): UIO[String]
}

// Module Pattern 2.0

case class NewsLive() extends News {
  val queue: UIO[Queue[String]] = Queue.unbounded[String]

  override def post(news: String): UIO[Unit] = for {
    q <- queue
    _ <- q.offer(news)
  } yield()

  override def proclaim(): UIO[String] = for {
    q <- queue
    s <- q.take
  } yield s
}

object News {
  def post(news: String): URIO[Has[News], Unit] = ZIO.serviceWith[News](_.post(news))

  def proclaim():URIO[Has[News], String] = ZIO.serviceWith[News](_.proclaim())
}

object NewsLive {
  val layer: ULayer[Has[News]] = ZLayer.succeed(NewsLive)
}
