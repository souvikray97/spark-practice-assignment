package org.souvik.application


import org.apache.spark.sql.functions.{col, current_timestamp, expr}
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{DataFrame, SparkSession}


object Main {
  def main(args: Array[String]): Unit = {
  val spark = SparkSession
    .builder()
    .appName("spark-practice2")
    .master("local[*]")
    .config("spark.driver.bindAddress","127.0.0.1")
    .getOrCreate()

    val df: DataFrame =spark
      .read
      .option("header", value = true)
      .option("inferSchema", value = true)
      .csv("data/AAPL.csv")

    df.show()
    df.printSchema()

    /*
    df.withColumnRenamed("Open", "open")
      .withColumnRenamed("Close", "close")
      .withColumnRenamed("High","high")
      .withColumnRenamed("Low", "low")
      .withColumnRenamed("Date", "date")
      .withColumnRenamed("Volume", "volume")
      .withColumnRenamed("Adj Close", "adj_close")
      .show()

     */

    val renameColumns = List(
      col("Date").as("date"),
      col("Open").as("open"),
      col("High").as("high"),
      col("Low").as("low"),
      col("Close").as("close"),
      col("Adj Close").as("adjClose"),
      col("Volume").as("volume")
    )

    val stockData = df.select(renameColumns: _*)
      .withColumn("diff", col("close") - col("open"))
      .filter(col("close") > col("open")*1.1)

    stockData.show(false)

    /* df.select(df.columns.map(c => col(c).as(c.toLowerCase())): _*).show() */

  }
  }
