package exercise4

import zio.{ExitCode, Has, ULayer, URIO, URLayer, ZLayer}

object Task1 extends zio.App {

  trait Config
  trait Logging
  trait Parsing
  trait Database
  trait Serialization
  trait UserService

  val configLive: ULayer[Has[Config]] = ???
  val userServiceLive: URLayer[Has[Database] with Has[Logging] with Has[Serialization], Has[UserService]] = ???
  val parsingLive: ULayer[Has[Parsing]] = ???
  val serializationLive: URLayer[Has[Parsing], Has[Serialization]] = ???
  val databaseLive: URLayer[Has[Config], Has[Database]] = ???
  val loggingLive: ULayer[Has[Logging]] = ???

  type MyEnv = Has[Database] with Has[Logging] with Has[Serialization] with Has[UserService] with Has[Parsing] with Has[Config]

  def f(): URIO[MyEnv, Unit] = ???

  /*
  Long version:
  val databaseLayer = configLive >>> databaseLive
  val serializationLayer = parsingLive >>> serializationLive
  val userServiceLayer = loggingLive ++ databaseLayer ++ serializationLayer >>> userServiceLive
  val layer = databaseLayer ++ loggingLive ++ serializationLayer ++ userServiceLayer ++ parsingLive ++ configLive
   */

  /**
   * Config, Parsing und Logging haben keine Requirements
   * und können daher direkt zu Beginn kombiniert werden
   *
   * Wenn wir Type-Annotations über IntelliJ einfügen, wird als Error-Type E1 angegeben, but why?
   */

  val layer = configLive ++ parsingLive ++ loggingLive >+> databaseLive ++ serializationLive >+> userServiceLive

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = f().provideCustomLayer(layer).exitCode
}
