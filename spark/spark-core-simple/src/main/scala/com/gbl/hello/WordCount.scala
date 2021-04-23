package com.gbl.hello

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

    def main(args: Array[String]): Unit = {
        val conf:SparkConf = new SparkConf().setAppName("wc")//.setMaster("local[*]")
        val sc:SparkContext = new SparkContext(conf)
        sc.setLogLevel("WARN")

        val lines:RDD[String] = sc.textFile("spark/datas/input/words.txt")

        val words:RDD[String] = lines.flatMap(_.split(" "))

        val wordAndOnes:RDD[(String, Int)] = words.map((_, 1))

        val result:RDD[(String, Int)] = wordAndOnes.reduceByKey(_ + _)


//        result.foreach(println)
//        println(result.collect().toBuffer)
        result.repartition(1).saveAsTextFile("spark/datas/inoutput/")

//        Thread.sleep(1000 * 60)

        sc.stop()

    }

}
