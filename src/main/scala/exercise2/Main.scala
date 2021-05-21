package exercise2

import exercise2.lib.{StdAudio, Synthesizer}

object Main extends App {

  val a = Ior.right(2) // Right(2)
  println(a)

  val b = a.map(x => x * 4) // Right(8)
  println(b)

  val c = b.flatMap(_ => Ior.right[Throwable, String]("a string")) //Right("a string")
  println(c)

  val d = c.flatMap(_ => Ior.left[Throwable, String](new RuntimeException("a grave error"))) //Left(java.lang.RuntimeException: a grave error)
  println(d)

  val e = d.map(x => x + "something") //Left(java.lang.RuntimeException: a grave error)
  println(e)

  val both = Ior.both(new RuntimeException("not fatal"), 21) //Both(java.lang.RuntimeException: not fatal,21)
  println(both)

  val both1 = both.map(x => x * 2) //Both(java.lang.RuntimeException: not fatal,42)
  println(both1)

  val both2 = both.flatMap(_ => Ior.left[Throwable, Int](new RuntimeException("fatal error"))) //Left(java.lang.RuntimeException: fatal error)
  println(both2)

  val both3 = both.flatMap(_ => Ior.right[Throwable, Int](480)) //Both(java.lang.RuntimeException: not fatal,480)
  println(both3)

  val both4 = both.flatMap(x => Ior.both[Throwable, Int](new RuntimeException("another not fatal"), x * 3)) //Both(java.lang.RuntimeException: another not fatal,63)
  println(both4)

  // frequency parameter is actually wavelength
  // "Alle meine Entchen" aber mit falschen Tonlängen :)

  val c1 = Synthesizer.whiteNoise(131) // 131 // 262
  val d1 = Synthesizer.whiteNoise(117) // 117 // 294
  val e1 = Synthesizer.whiteNoise(104) // 104 // 330
  val f1 = Synthesizer.whiteNoise(98) // 98 // 349
  val g1 = Synthesizer.whiteNoise(87) // 87 // 392
  val a1 = Synthesizer.whiteNoise(78) // 78 // 440
  val h1 = Synthesizer.whiteNoise(69) // 69 // 493

  Synthesizer.loop(c1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(d1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(e1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(f1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(g1)(StdAudio.play) // 2
  Thread.sleep(200)
  Synthesizer.loop(g1)(StdAudio.play) // 2
  Thread.sleep(200)
  Synthesizer.loop(a1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(a1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(a1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(a1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(g1)(StdAudio.play) // 4
  Thread.sleep(400)
  Synthesizer.loop(a1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(a1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(a1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(a1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(g1)(StdAudio.play) // 4
  Thread.sleep(400)
  Synthesizer.loop(f1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(f1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(f1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(f1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(e1)(StdAudio.play) // 2
  Thread.sleep(200)
  Synthesizer.loop(e1)(StdAudio.play) // 2
  Thread.sleep(200)
  Synthesizer.loop(g1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(g1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(g1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(g1)(StdAudio.play) // 1
  Thread.sleep(100)
  Synthesizer.loop(c1)(StdAudio.play) // 4

}
