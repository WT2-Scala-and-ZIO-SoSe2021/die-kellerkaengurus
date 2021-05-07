package exercise2

object Main extends App {

  val a = Ior.right(2) // Right(2)
  println(a)

  val b = a.map(x => x * 4) // Right(8)
  val x = a.flatMap(x => Ior.unit(new Throwable))
  println(b)
  println(x)

  val c = b.flatMap(_ => Ior.right("a string")) //Right("a string")
  println(c)

  val d = c.flatMap(_ => Ior.left[String](new RuntimeException("a grave error"))) //Left(java.lang.RuntimeException: a grave error)
  println(d)

  val e = d.map(x => x + "something") //Left(java.lang.RuntimeException: a grave error)
  println(e)

  val both = Ior.both(new RuntimeException("not fatal"), 21) //Both(java.lang.RuntimeException: not fatal,21)
  println(both)

  val both1 = both.map(x => x * 2) //Both(java.lang.RuntimeException: not fatal,42)
  println(both1)

  val both2 = both.flatMap(_ => Ior.left[Int](new RuntimeException("fatal error"))) //Left(java.lang.RuntimeException: fatal error)
  println(both2)

  val both3 = both.flatMap(_ => Ior.right(480)) //Both(java.lang.RuntimeException: not fatal,480)
  println(both3)

  val both4 = both.flatMap(x => Ior.both(new RuntimeException("another not fatal"), x * 3)) //Both(java.lang.RuntimeException: another not fatal,63)
  println(both4)
}
