package com.gbl.bigdata.spark.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_WordCount {

    def main(args: Array[String]): Unit = {

        // Application

        //Spark框架

        // TODO 建立和Spark框架的连接

        // JDBC: Connection
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        // TODO 执行业务操作
        val lines: RDD[String] = sc.textFile("spark/datas")

        val words: RDD[String] = lines.flatMap(_.split(" "))

        val wordGroup: RDD[(String, Iterable[String])] = words.groupBy(word => word)

        val wordToCount = wordGroup.map {
            case (word, list) => {
                (word, list.size)
            }
        }

        val tuples = wordToCount.collect()

        tuples.foreach(println)

        // TODO 关闭连接

        sc.stop()
    }

}
