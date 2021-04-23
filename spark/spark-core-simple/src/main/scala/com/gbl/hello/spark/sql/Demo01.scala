package com.gbl.hello.spark.sql

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}

object Demo01 {
    def main(args: Array[String]): Unit = {
        import org.apache.spark
        val sparkSession: SparkSession = SparkSession.builder()
                .appName("sparksql")
                .master("local[*]")
                .getOrCreate()

        val ds: Dataset[String] = sparkSession.read.textFile("spark/datas/input/person.txt")
        ds.printSchema()
        ds.show()


//        val context: SparkContext = sparkSession.sparkContext
//        context.setLogLevel("WARN")
//
//        val lines: RDD[String] = context.textFile("datas/input/person.txt")
//        val personRDD: RDD[Person] = lines.map(line => {
//            val arr: Array[String] = line.split("    ")
//            Person(arr(0).toInt, arr(1), arr(2).toInt)
//        })
        sparkSession.stop()

    }

    case class Person(id:Int, name:String, age:Int)
}
