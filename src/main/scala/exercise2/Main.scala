package exercise2

import exercise2.lib.{StdAudio, Synthesizer}

object Main extends App {

  val a = Ior.right(2) // Right(2)
  println(a)

  val b = a.map(x => x * 4) // Right(8)
  println(b)

  val c = b.flatMap(_ => Ior.right("a string")) //Right("a string")
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

  val both3 = both.flatMap(_ => Ior.right(480)) //Both(java.lang.RuntimeException: not fatal,480)
  println(both3)

  val both4 = both.flatMap(x => Ior.both(new RuntimeException("another not fatal"), x * 3)) //Both(java.lang.RuntimeException: another not fatal,63)
  println(both4)

  val whiteNoise = Synthesizer.whiteNoise(frequency = 100)
  Synthesizer.loop(whiteNoise)(StdAudio.play)

  val a1 = Synthesizer.whiteNoise()
  val d1 = Synthesizer.whiteNoise(294)
  val e1 = Synthesizer.whiteNoise(330)
  val f1 = Synthesizer.whiteNoise(349)
  val g1 = Synthesizer.whiteNoise(392)
  val h1 = Synthesizer.whiteNoise(493)

  Synthesizer.loop(d1)(StdAudio.play) // kurz
  Thread.sleep(200)
  Synthesizer.loop(e1)(StdAudio.play) // kurz
  Synthesizer.loop(f1)(StdAudio.play) // kurz
  Synthesizer.loop(g1)(StdAudio.play) // kurz
  Synthesizer.loop(a1)(StdAudio.play) // lang
  Synthesizer.loop(a1)(StdAudio.play) // lang
  Synthesizer.loop(h1)(StdAudio.play) // kurz
  Synthesizer.loop(h1)(StdAudio.play) // kurz
  Synthesizer.loop(h1)(StdAudio.play) // kurz
  Synthesizer.loop(h1)(StdAudio.play) // kurz
  Synthesizer.loop(a1)(StdAudio.play) // lang
  // pause
  Synthesizer.loop(g1)(StdAudio.play) // kurz
  Synthesizer.loop(g1)(StdAudio.play) // kurz
  Synthesizer.loop(g1)(StdAudio.play) // kurz
  Synthesizer.loop(g1)(StdAudio.play) // kurz
  Synthesizer.loop(f1)(StdAudio.play) // lang
  Synthesizer.loop(f1)(StdAudio.play) // lang
  Synthesizer.loop(a1)(StdAudio.play) // kurz
  Synthesizer.loop(a1)(StdAudio.play) // kurz
  Synthesizer.loop(a1)(StdAudio.play) // kurz
  Synthesizer.loop(a1)(StdAudio.play) // kurz
  Synthesizer.loop(d1)(StdAudio.play) // lang

}
