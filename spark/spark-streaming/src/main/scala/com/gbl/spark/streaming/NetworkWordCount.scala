package com.gbl.spark.streaming

import java.io.{File, PrintWriter}

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author guobaolin
 * @date 2021/4/23
 * @Description: ${todo}
 */
object NetworkWordCount {
    def main(args: Array[String]) = {
        val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(5))
        val lines: DStream[String] = ssc.socketTextStream("127.0.0.1", 9999)
        val words: DStream[String] = lines.flatMap(_.split(","))
        val pairs: DStream[(String, Int)] = words.map((_, 1))
        val wordCounts: DStream[(String, Int)] = pairs.reduceByKey(_ + _)
        ssc.sparkContext.setLogLevel("ERROR")
        wordCounts.print()
        println(wordCounts)
        ssc.start()
        ssc.awaitTermination()

//        val writer: PrintWriter = new PrintWriter(new File("D:/github/sf/big-data-freamwork/spark/datas/streaming1.txt"))
//        writer.write("hello,world,spark,java,scala,spark,java")
//        writer.close()
    }
}
