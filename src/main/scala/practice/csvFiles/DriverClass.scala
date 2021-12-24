package practice.csvFiles

object DriverClass extends App {

  val list = List(1, 2, 3, 4, 5, 6, 7)

  val in = list.map(x => demoFun(x))

  def demoFun(xValue: Int): Int = {
    if(xValue % 2 == 0) xValue + 5 else xValue
  }





  println(in)
}
