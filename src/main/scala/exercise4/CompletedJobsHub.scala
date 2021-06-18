package exercise4

import zio.{Dequeue, Has, Hub, UIO, ULayer, URIO, ZIO, ZLayer, ZManaged}

trait CompletedJobsHub {
  def subscribe: ZManaged[Any, Nothing, Dequeue[CompletedJob]]

  def publish(job: CompletedJob): UIO[Unit]
}

case class CompletedJobsHubLive(hub: Hub[CompletedJob]) extends CompletedJobsHub {
  override def subscribe: ZManaged[Any, Nothing, Dequeue[CompletedJob]] = hub.subscribe

  override def publish(job: CompletedJob): UIO[Unit] = hub.publish(job).unit
}

object CompletedJobsHub {
  def subscribe(): URIO[Has[CompletedJobsHub], ZManaged[Any, Nothing, Dequeue[CompletedJob]]] = ZIO.serviceWith[CompletedJobsHub](_.subscribe)

  def publish(job: CompletedJob): URIO[Has[CompletedJobsHub], Unit] = ZIO.serviceWith[CompletedJobsHub](_.publish(job))
}

object CompletedJobsHubLive {
  val layer: ULayer[Has[CompletedJobsHub]] = ZLayer.fromEffect(Hub.unbounded[CompletedJob].map(hub => CompletedJobsHubLive(hub)))
}