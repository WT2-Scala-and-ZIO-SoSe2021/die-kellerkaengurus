object Main extends App {
  println("Hello, world!")

  // TODO: TASK 1
  def max(arr: Array[Int]): Int = arr.reduce((x, y) => x max y)
  def min(arr: Array[Int]): Int = arr.reduce((x,y) => x min y)
  def sum(arr: Array[Int]): Int = arr.reduce((x,y) => x + y)

  val x = Array(1,2,3)
  val sumVal = sum(x)
  print(sumVal)

  // TODO: TASK 2
}
