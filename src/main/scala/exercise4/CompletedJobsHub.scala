package exercise4

import zio.{Dequeue, Has, Hub, Managed, UIO, ULayer, UManaged, URIO, URManaged, ZIO, ZLayer}

trait CompletedJobsHub {
  def subscribe: UManaged[Dequeue[CompletedJob]]

  def publish(job: CompletedJob): UIO[Unit]
}

case class CompletedJobsHubLive(hub: Hub[CompletedJob]) extends CompletedJobsHub {
  override def subscribe: UManaged[Dequeue[CompletedJob]] = hub.subscribe

  override def publish(job: CompletedJob): UIO[Unit] = hub.publish(job).unit
}

object CompletedJobsHub {
  def subscribe(): URManaged[Has[CompletedJobsHub], Dequeue[CompletedJob]] = Managed.accessManaged[Has[CompletedJobsHub]](_.get.subscribe)

  def publish(job: CompletedJob): URIO[Has[CompletedJobsHub], Unit] = ZIO.serviceWith[CompletedJobsHub](_.publish(job))
}

object CompletedJobsHubLive {
  val layer: ULayer[Has[CompletedJobsHub]] = ZLayer.fromEffect(Hub.unbounded[CompletedJob].map(hub => CompletedJobsHubLive(hub)))
}