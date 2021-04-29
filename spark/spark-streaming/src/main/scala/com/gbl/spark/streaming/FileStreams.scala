package com.gbl.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author guobaolin
 * @date 2021/4/23
 * @Description: ${todo}
 */
object FileStreams {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf().setMaster("local[4]").setAppName("FileStreams")
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(6))
        ssc.sparkContext.setLogLevel("ERROR")
        val lines: DStream[String] = ssc.textFileStream("file:///D:/github/sf/big-data-freamwork/spark/datas")
        println(lines)
        lines.print()
        ssc.start()
        ssc.awaitTermination()
    }
}
