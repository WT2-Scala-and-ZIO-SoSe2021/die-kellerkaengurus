import exercise3.Locales
import exercise3.Locales.Locale

package object temperature {
  type Temperature = Double

  implicit class TemperatureConverter(d: Temperature) {
    val FREEZING_POINT: Temperature = 0
    val ABSOLUTE_ZERO: Temperature = -273.15

    def fahrenheit: Temperature = d - 32 * 1.8

    def kelvin: Temperature = d + ABSOLUTE_ZERO

    def celsius: Temperature = d

    def avg(other: Temperature): Temperature = (d + other) / 2
  }

  def display(temperature: Temperature)(implicit locale: Locale = Locales.Other): String = {
    locale match {
      case Locales.Other => temperature.celsius + "°C"
      case Locales.US => temperature.fahrenheit + "°F"
      case Locales.SCI => temperature.kelvin + "°K"
    }
  }
}
