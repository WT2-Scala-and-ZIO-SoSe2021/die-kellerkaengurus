object Main extends App {
  // TASK 1
  def max(arr: Array[Int]): Option[Int] = if (arr.isEmpty) None else Some(arr.reduce((x, y) => x max y))

  def min(arr: Array[Int]): Option[Int] = if (arr.isEmpty) None else Some(arr.reduce((x, y) => x min y))

  def sum(arr: Array[Int]): Int = arr.fold(0)((x, y) => x + y)

  // TODO: TASK 2
}
