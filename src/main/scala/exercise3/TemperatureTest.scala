package exercise3

import exercise3.Locales.Locale
import temperature._

class TemperatureTest {
  def run(): Unit = {
    implicit val l: Locale = Locales.Other

    val t1 = 86.345.fahrenheit
    val t2 = 3.6.celsius

    println(display(t1))
    println(display(t2))

    println(display(t1 avg t2))

    println(display(25.8.celsius))
  }
}
