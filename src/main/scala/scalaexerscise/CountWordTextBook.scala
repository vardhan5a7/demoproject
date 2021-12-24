package scalaexerscise


import org.apache.log4j._
import org.apache.spark.SparkContext

object CountWordTextBook extends  App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sc = new SparkContext("local[*]", "CountWordTextBook")

  val input = sc.textFile("src/main/resources/DemoTextBook.txt")

  val words = input.flatMap(x => x.split(" "))

  println(words)
  val wordCount = words.countByValue()

  wordCount.foreach(println)

  println(wordCount)
}
