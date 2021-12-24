package practice.RDD

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object sparkAssinment1 extends App {

  //set the log level to print only errors
  Logger.getLogger("org").setLevel(Level.ERROR)

  //create a sparkContext using every core of the local mechine
  val sc = new SparkContext("local[*]", "sparkAssinment1")

  //Load each line of source data into to RDD
  val userDataLines = sc.textFile("src/main/resources/userData.csv")
  val transactionLines = sc.textFile("src/main/resources/transactions.csv")

  //use parseLines function to convert to (feilds) to tuples
  def parseLine1(line: String): (Int, String) = {

    val fields = line.split(",")

    val userId = fields(0).toInt
    val location = fields(3)
    (userId, location)
  }

  def parseLine2(line: String): (Int, Int, String) = {
    val fields = line.split(",")
    val userId = fields(2).toInt
    val price = fields(3).toInt
    val soldData = fields(4)
    (userId, price, soldData)
  }

  val filterRDD1 = userDataLines.filter(x => x != userDataLines.first())
  val filterRDD2 = transactionLines.filter(x => x != transactionLines.first())

  val rdd1 = filterRDD1.map(parseLine1)
  val rdd2 = filterRDD2.map(parseLine2)

  val col1: List[(Int, String)] = rdd1.collect().toList
  val col2: List[(Int, Int, String)] = rdd2.collect().toList


  def countUniqueLocation(): Int = {

    println(col1.distinct)
    println(col2)

    val sortTheDataLocation = col1.flatMap(x => col2.map(y => if (x._1 == y._1) (x._1 -> x._2)))
    val findOutProducts = sortTheDataLocation.distinct
    val uniqueLocations = col1.groupBy(m => (m._2)).keys.toList
    val sortedData = findOutProducts.filter(x => x.!=())
    println(sortedData)
    println(uniqueLocations)
    //Count of unique locations where each product is sold.
    val coutTotal = uniqueLocations.size
    coutTotal
  }

  def findProductsByEachUser(): List[Any] = {
    val sortTheData = col1.flatMap(x => col2.map(y => if (x._1 == y._1) (y._3)))
    val sortTheProducts = sortTheData.filter(x => x != ())
    sortTheProducts
  }

  def findTotalAmount(): Int = {
    var totalSum = 0
    val sortTheData = col1.flatMap(x => col2.map(y => if (x._1 == y._1) totalSum += y._2))

    val sortTheDataUser = col1.flatMap(x => col2.map(y => if (x._1 == y._1) (x._1, y._2, y._3)))
    val sortDataUserValues = sortTheDataUser.filter(x => x != ())

    println(sortDataUserValues)
    totalSum
  }

  println("Total no.of unique location ")
  println(countUniqueLocation + "\n")
  println("Total products bought by each user ")
  println(findProductsByEachUser + "\n")
  println("Total spending done by each user on each product")
  println(findTotalAmount)
}
