package exercise4

import zio.{Has, Queue, UIO, ULayer, URIO, ZIO, ZLayer}

trait News {
  def post(news: String): UIO[Unit]

  def proclaim(): UIO[String]
}

// Module Pattern 2.0

case class NewsLive(queue: Queue[String]) extends News {
  override def post(news: String): UIO[Unit] = for {
    _ <- queue.offer(news)
  } yield()

  override def proclaim(): UIO[String] = for {
    s <- queue.take
  } yield s
}

object News {
  def post(news: String): URIO[Has[News], Unit] = ZIO.serviceWith[News](_.post(news))

  def proclaim():URIO[Has[News], String] = ZIO.serviceWith[News](_.proclaim())
}

object NewsLive {
  val layer: ULayer[Has[News]] = ZLayer.fromEffect(Queue.unbounded[String].map(queue => NewsLive(queue)))
}
