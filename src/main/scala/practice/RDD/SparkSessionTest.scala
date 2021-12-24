package practice.RDD

import org.apache.spark.sql.SparkSession

object SparkSessionTest extends App {

  //set the log level to print only errors
  //Logger.getLogger("org").setLevel(Level.ERROR)

  val spark = SparkSession.builder()
    .master("local[*]")
    .appName("SparkByExample")
    .getOrCreate();

  println("First SparkContext:")
  println("APP Name :" + spark.sparkContext.appName);
  println("Deploy Mode :" + spark.sparkContext.deployMode);
  println("Master :" + spark.sparkContext.master);
  println("vardhan")

  val sparkSession2 = SparkSession.builder()
    .master("local[1]")
    .appName("SparkByExample-test")
    .getOrCreate();

  println("Second SparkContext:")
  println("APP Name :" + sparkSession2.sparkContext.appName);
  println("Deploy Mode :" + sparkSession2.sparkContext.deployMode);
  println("Master :" + sparkSession2.sparkContext.master);

  val userData = spark.read.format("csv").option("header", "true").load("src/main/scala/practice/csvFiles/userData.csv")

  //    val userData = spark.read.format("csv").option("header", "true") .option("inferSchema", "true").load("src/main/scala/practice/csvFiles/userData.csv").show()
  val transaction = spark.read.format("csv").option("header", "true").load("src/main/scala/practice/csvFiles/transactions.csv")

  //val dataRDD  = spark.read.csv("src/main/scala/practice/csvFiles/userData.csv").rdd

  println(userData)
  println(transaction)

  println(userData.encoder)

}
