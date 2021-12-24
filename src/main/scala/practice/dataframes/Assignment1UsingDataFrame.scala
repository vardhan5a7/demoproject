package practice.dataframes

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.log4j._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions._
import org.apache.spark.rdd.RDD
import practice.RDD.SparkSessionTest.{spark, sparkSession2}

object Assignment1UsingDataFrame extends App{

    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkConf = new SparkConf()
    sparkConf.set("spark.app.name", "spark_df")
    sparkConf.set("spark.master", "local[2]")

    val spark = SparkSession.builder()
      .config(sparkConf)
      .getOrCreate()

    val df_1 = spark.read
      .format("csv")
      .option("header",true)
      .option("path","src/main/resources/userData.csv")
      .load()
    df_1.show()
    val df_2 = spark.read
      .format("csv")
      .option("header",true)
      .option("path","src/main/resources/transactions.csv")
      .load()
    df_2.show()

    val joined_df = df_1.join(df_2,df_1("UserId") === df_2("UserId"),"outer").drop(df_2("UserId"))
    joined_df.show()

    val distinct_count = joined_df.select(countDistinct("Location") as "dis_location").show()
    val count_products = joined_df.groupBy("UserId")
      .agg(expr("count(Product_Id) As Total")).orderBy("UserId")
      .show()
    val price_count = joined_df.select("UserId","Price","Product Description")
      .groupBy("UserId","Product Description")
      .agg(expr("sum(Price) as Total_spending")).orderBy("UserId")
      .show()

}
