object Main extends App {
  // TASK 1
  def max(arr: Array[Int]): Option[Int] = if (arr.isEmpty) None else Some(arr.reduce((x, y) => x max y))

  def min(arr: Array[Int]): Option[Int] = if (arr.isEmpty) None else Some(arr.reduce((x, y) => x min y))

  def sum(arr: Array[Int]): Int = arr.fold(0)((x, y) => x + y)

  // TASK 2
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

  def parseAll(cards: Array[String]): Int = {
    sum(cards.map(card => parse(card).getOrElse(0)))
  }

  def values(card: Int): Array[Int] = if (card == 11) Array(1, 11) else Array(card)

  def isBust(handValue: Int): Boolean = handValue > 21

  def determineHandValue(strategy: Array[Int] => Option[Int])(hand: Array[Int]): Int = {
    sum(hand.map(card => values(card)).flatMap(cardValues => strategy(cardValues)))
  }

  def optimisticF: Array[Int] => Int = determineHandValue(max)(_)

  def pessimisticF: Array[Int] => Int = determineHandValue(min)(_)

  def determineBestHandValue(hand: Array[Int]): Int = {
    /*
    // Sort hand -> Array(9, 11, 11)
    val sortedHand = hand.sorted
    // Array(Array(9), Array(1, 11), Array(1, 11))
    val firstAceIndex = sortedHand.indexOf(11)
    val aces = sortedHand.slice(firstAceIndex, sortedHand.length)
    val sumWithoutAces = sum(sortedHand.slice(0, firstAceIndex))

    // either bust or optimal (21) using pessimistic aces
    if (isBust(sumWithoutAces) || sumWithoutAces + aces.length >= 21) {
      return pessimisticF(aces) + sumWithoutAces
    }
    */
    val optimistic = optimisticF(hand)
    if (isBust(optimistic)) pessimisticF(hand) else optimistic
  }
}
