package edu.mock

import java.util.Properties
import java.util.concurrent.{ArrayBlockingQueue, ThreadPoolExecutor, TimeUnit}

import com.google.gson.Gson
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.LoggerFactory

object KafkaProducerTest {
    def main(args: Array[String]): Unit = {
        // 创建线程池
        val threadPoolExecutor: ThreadPoolExecutor = new ThreadPoolExecutor(5, 10,
            5, TimeUnit.SECONDS, new ArrayBlockingQueue[Runnable](10))
        // 提交任务
        for (i <- 1 to 4) {
            threadPoolExecutor.submit(new KafkaProducerThread)
        }
    }

}

/**
 * 发送数据到Kafka的生产者线程对象
 */
class KafkaProducerThread extends Thread {
    val logger = LoggerFactory.getLogger(classOf[KafkaProducerThread])

    val props = new Properties()
    props.setProperty("bootstrap.servers", "node01:9092,node02:9092,node03:9092")
    props.setProperty("ack", "1")
    props.setProperty("batch.size", "16384")
    props.setProperty("linger.ms", "5")
    props.setProperty("buffer.memory", "33554432")
    props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)
    val gson: Gson = new Gson()

    override def run(): Unit = {
        while (true) {
            val question = Simulator.getQuestion()
            val jsonString: String = gson.toJson(question)

            producer.send(new ProducerRecord[String, String]("edu", jsonString), new Callback {
                override def onCompletion(metadata: RecordMetadata, e: Exception): Unit = {
                    if (e == null) {
                        println("当前分区—偏移量：" + metadata.partition() + "-" + metadata.offset() +
                                "\n数据发送成功：" + jsonString)
                        logger.info("当前分区—偏移量：" + metadata.partition() + "-" + metadata.offset() +
                                "\n数据发送成功：" + jsonString)
                    } else {
                        logger.error("数据发送失败" + e.getMessage)
                    }
                }
            })
            Thread.sleep(300)
        }
    }

}