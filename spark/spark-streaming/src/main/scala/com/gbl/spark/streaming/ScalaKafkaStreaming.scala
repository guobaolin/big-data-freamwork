package com.gbl.spark.streaming

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author guobaolin
 * @date 2021/4/29
 * @Description: spark streaming consumer kafka
 */
object ScalaKafkaStreaming {
    def main(args: Array[String]): Unit = {
        // offset保存路径
        val checkpointPath = "checkpoint/kafka-direct"

        val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("ScalaKafkaStream")
        val sc: SparkContext = new SparkContext(conf)
        sc.setLogLevel("WARN")

        val ssc: StreamingContext = new StreamingContext(sc, Seconds(5))
        ssc.checkpoint(checkpointPath)

        val bootstrapServers: String = "100.80.144.159:9092,100.80.144.160:9092,100.80.144.171:9092,100.80.144.172:9092,100.80.144.173:9092"
        val groupId ="kafka-test-group"
        val topicName = "FOP_DMP_DSP_RESOURCE_REQUIRE"
        val maxPoll = 10
        val kafkaParams = Map(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG -> groupId,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG -> maxPoll.toString,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
            "auto.offset.reset" -> "latest",
            "enable.auto.commit" -> (false: java.lang.Boolean)
        )

        val kafkaTopicDS: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](ssc,
            LocationStrategies.PreferConsistent, ConsumerStrategies.Subscribe[String, String](Array(topicName), kafkaParams))

        kafkaTopicDS.map(record => (record.key, record.value)).print()

        ssc.start()
        ssc.awaitTermination()
    }
}
