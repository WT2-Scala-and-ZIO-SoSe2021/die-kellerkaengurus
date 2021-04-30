package exercise1

object Main extends App {
  // TASK 1
  def max(arr: Array[Int]): Option[Int] = if (arr.isEmpty) None else Some(arr.reduce((x, y) => x max y))

  def min(arr: Array[Int]): Option[Int] = if (arr.isEmpty) None else Some(arr.reduce((x, y) => x min y))

  def sum(arr: Array[Int]): Int = arr.fold(0)((x, y) => x + y)


  /**
   * Returns a card's value as an integer.
   * Returns None if the string passed is not a valid card.
   *
   * @param card The card representation as string
   * @return The card's value
   */
  def parse(card: String): Option[Int] = {
    card match {
      case "A" => Some(11)
      case "J" | "Q" | "K" => Some(10)
      case _ =>
        try {
          val number = card.toInt
          if (number < 2 || number > 10) None else Some(number)
        } catch {
          case _: NumberFormatException => None
        }
    }
  }

  /**
   * Takes an array of strings and calls parse for each element
   *
   * @param cards An array of card strings
   * @return The values of the cards as integers
   */
  def parseAll(cards: Array[String]): Array[Int] = {
    cards.map(card => parse(card).getOrElse(0)).filter(x => x != 0)
  }

  /**
   * Takes a card (Int) and returns its possible values (Array[Int]).
   * In case of aces an array containing 2 elements is returned (11 and 1).
   * In all other cases, an array contains a single element is returned (with the value of the passed card/Integer).
   *
   * @param card The card for which to retrieve the possible
   * @return A list of the possible values for the card
   */
  def values(card: Int): Array[Int] = if (card == 11) Array(1, 11) else Array(card)

  /**
   * Takes the hand value and returns true if is greater than 21 or false otherwise.
   *
   * @param handValue The value of the hand
   * @return True if the value is bigger than the limit of 21, otherwise false
   */
  def isBust(handValue: Int): Boolean = handValue > 21

  /**
   * Determines the hand value (return type Int).
   * It should be curried and accept a strategy function (Array[Int] => Int),
   * which maps the values of a card to a single value and the hand (Array[Int]).
   *
   * @param strategy The strategy to use for determining the hand value
   * @param hand     The hand cards array
   * @return The value of the hand
   */
  def determineHandValue(strategy: Array[Int] => Option[Int])(hand: Array[Int]): Int = {
    sum(hand.map(card => values(card)).flatMap(cardValues => strategy(cardValues)))
  }

  /**
   * Applies max as a strategy to determineHandValue.
   *
   * @return
   */
  def optimisticF: Array[Int] => Int = determineHandValue(max)(_)

  /**
   * Applies min as a strategy to determineHandValue.
   *
   * @return
   */
  def pessimisticF: Array[Int] => Int = determineHandValue(min)(_)

  /**
   * Takes a hand and computes its value using optimisticF
   * and if this results in a bust returns pessimisticF instead.
   *
   * @param hand The hand card values
   * @return The best possible result
   */
  def determineBestHandValue(hand: Array[Int]): Int = {
    val optimistic = optimisticF(hand)

    // Special case: two aces
    if (hand.count(x => x == 11) == 2) {
      // Check if aces (1, 11) is greater than 21 -> use aces (1, 1)
      // Else use aces (1, 11)
      if (isBust(optimistic - 10)) return pessimisticF(hand) else return optimistic - 10
    }

    if (isBust(optimistic)) pessimisticF(hand) else optimistic
  }
}
