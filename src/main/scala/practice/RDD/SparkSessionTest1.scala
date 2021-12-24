package practice.RDD

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object SparkSessionTest1 extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)
  val spark = SparkSession.builder()
    .master("local[*]")
    .appName("SparkByExample")
    .getOrCreate();

  val rdd1 =spark.sparkContext.textFile("src/main/resources/userData.csv")
  val rdd2 = spark.sparkContext.textFile("src/main/resources/transactions.csv")
  val with_header = rdd1.first()
  val without_header = rdd1.filter(x => x!=with_header)
  val rdd1_trans = without_header
    .map(x => x.split(","))
    .map(x => x(3))
    .distinct()
  //counting distinct locations
  rdd1_trans.foreach(println)

  val with_header1 = rdd2.first()
  val without_header2 = rdd2.filter(x => x!=with_header1)
  val rdd_trans = without_header2.map(x => {
    val fields = x.split(",")
    val product = fields(1)
    val user = fields(2)
    (user,product)
  })

  val rdd_trans2 = rdd_trans.map(x => (x._1, x._2))
  val new_rdd =  rdd_trans2.map(x => (x._1,(x._2,1)))
  val trans_rdd = new_rdd.reduceByKey((x,y) => (x._1 + y._2 , x._2 + y._2))
  val aftr_tran = trans_rdd.map(x => (x._1,x._2._2)).sortBy(x => x._1)
  aftr_tran.foreach(println)


  val split_rdd = without_header2.map(x => x.split(","))
    .map(x => (x(2).toInt,x(3).toInt))
    .map(x => (x._1,(x._2,1)))
    .reduceByKey((x,y) => (x._1 + y._1 ,x._2 + y._2) )
    .map( x=> (x._1,x._2._1))
    .sortBy(x => x._1)

  println("price_value")

  split_rdd.foreach(println)
}
