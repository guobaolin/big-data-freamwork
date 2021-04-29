package com.gbl.spark.streaming

import java.time.{Instant, LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.expressions.{Alias, Literal}
import org.apache.spark.sql.catalyst.expressions.aggregate.Count
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}

/**
 * @author guobaolin
 * @date 2021/4/23
 * @Description: ${todo}
 */
object SqlNetworkWordCount {

    val checkpointDirectory = "./cpk";

    def main(args: Array[String]): Unit = {
        val ssc = StreamingContext.getOrCreate(checkpointDirectory, functionToCreateContext _)
        ssc.start()
        ssc.awaitTermination()
    }

    def functionToCreateContext(): StreamingContext = {
        val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(5))

        ssc.checkpoint(checkpointDirectory)

        // 日志输出
        ssc.sparkContext.setLogLevel("ERROR")
        val lines: ReceiverInputDStream[String] = ssc.socketTextStream("127.0.0.1", 9999)
        val words: DStream[String] = lines.flatMap(_.split(" "))
        val wordCounts: DStream[(String, Int)] = words.map((_, 1)).reduceByKey(_ + _)
        wordCounts.foreachRDD { (rdd: RDD[(String, Int)],time: Time) =>
            
        }

//        words.foreachRDD { (rdd: RDD[String], time: Time) =>
//            // Get the singleton instance of SparkSession
//            val spark: SparkSession = SparkSessionSingleton.getInstance(rdd.sparkContext.getConf)
//            import spark.implicits._
//
//            // Convert RDD[String] to RDD[case class] to DataFrame
//            val wordsDataFrame: DataFrame = rdd.map(w => Record(w)).toDF()
//
//            // Creates a temporary view using the DataFrame
//            wordsDataFrame.createOrReplaceTempView("words")
//
//            // Do word count on table using SQL and print it
//            //            val wordCountsDataFrame: DataFrame = spark.sql("select word, count(*) as total from words group by word")
//
//            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//            val instant: Instant = Instant.ofEpochMilli(time.milliseconds)
//            val localDateTime: LocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai"))
//            val dateFormat: String = localDateTime.format(formatter)
//
//            println(s"========= $dateFormat =========")
//            import org.apache.spark.sql.functions._
//            wordsDataFrame.groupBy('word)
//                .agg(count('word).as("total"))
//                //                .count().as("total")
//                .show()
//
//            //            wordCountsDataFrame.show()
//        }
        ssc
    }

}

/** Case class for converting RDD to DataFrame */
case class Record(word: String)

/** Lazily instantiated singleton instance of SparkSession */
object SparkSessionSingleton {
    @transient private var instance: SparkSession = _

    def getInstance(sparkConf: SparkConf): SparkSession = {
        if (instance == null) {
            instance = SparkSession
                .builder()
                .config(sparkConf)
                .getOrCreate()
        }
        instance
    }
}