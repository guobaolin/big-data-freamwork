package com.gbl.hello.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object NetworkWordCount {
    def main(args: Array[String]): Unit = {

        // Create a local StreamingContext with two working thread and batch interval of 1 second.
        // The master requires 2 cores to prevent a starvation scenario.
        val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
        val ssc = new StreamingContext(conf, Seconds(5))
        ssc.checkpoint("./ckp")
        // Create a DStream that will connect to hostname:port, like localhost:9999
        val lines = ssc.socketTextStream("node01", 9999)
        //val lines = ssc.textFileStream("E:///dept.txt")
        // Split each line into words
        val words = lines.flatMap(_.split(" ")) // not necessary since Spark 1.3
        // Count each word in each batch
        val pairs = words.map(word => (word, 1))
        //        val wordCounts = pairs.reduceByKey(_ + _)
        val wordCounts = pairs.updateStateByKey {
            (currentValues: Seq[Int], historyValue: Option[Int]) =>
                if (currentValues.size > 0) {
                    val currentResult: Int = currentValues.sum + historyValue.getOrElse(0)
                    Some(currentResult)
                } else {
                    historyValue
                }
        }

        // Print the first ten elements of each RDD generated in this DStream to the console
        wordCounts.print()

        ssc.sparkContext.setLogLevel("ERROR")

        ssc.start() // Start the computation
        ssc.awaitTermination() // Wait for the computation to terminate
        ssc.stop(true, true)
    }
}
