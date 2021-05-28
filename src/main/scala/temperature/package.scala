import exercise3.Locales
import exercise3.Locales.Locale

package object temperature {
  type Temperature = Double

  implicit class TemperatureConverter(d: Temperature) {
    val FREEZING_POINT: Temperature = 0
    val ABSOLUTE_ZERO: Temperature = 0.0.kelvin

    def fahrenheit: Temperature = (d * 9/5) + 32

    def kelvin: Temperature = d - 273.15

    def celsius: Temperature = d

    def avg(other: Temperature): Temperature = (d + other) / 2
  }

  def display(temperature: Temperature)(implicit locale: Locale = Locales.Other): String = {
    locale match {
      case Locales.Other => temperature.celsius + " °C"
      case Locales.US => temperature.fahrenheit + " °F"
      case Locales.SCI => temperature.kelvin + " °K"
    }
  }
}
