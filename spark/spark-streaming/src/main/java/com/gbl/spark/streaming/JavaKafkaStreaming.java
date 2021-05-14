package com.gbl.spark.streaming;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guobaolin
 * @date 2021/4/29
 * @Description: KafkaStreaming
 */
public class JavaKafkaStreaming {
    public static void main(String[] args) throws InterruptedException {

        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("JavaKafkaStream");
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(5));

        Map<String, Object> kafkaParams = new HashMap<>();
        //Kafka服务监听端口
        kafkaParams.put("bootstrap.servers", "100.80.144.159:9092");
        //指定kafka输出key的数据类型及编码格式（默认为字符串类型编码格式为uft-8）
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        //指定kafka输出value的数据类型及编码格式（默认为字符串类型编码格式为uft-8）
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        //消费者ID，随意指定
        kafkaParams.put("group.id", "jis");
        //指定从latest(最新,其他版本的是largest这里不行)还是smallest(最早)处开始读取数据
        kafkaParams.put("auto.offset.reset", "latest");
        //如果true,consumer定期地往zookeeper写入每个分区的offset
        kafkaParams.put("enable.auto.commit", false);

        //要监听的Topic，可以同时监听多个
        Collection<String> topics = Arrays.asList("FOP_DMP_DSP_RESOURCE_REQUIRE");

        JavaInputDStream<ConsumerRecord<String, String>> directStream = KafkaUtils.createDirectStream(
            jsc,
            LocationStrategies.PreferConsistent(),
            ConsumerStrategies.Subscribe(topics, kafkaParams)
        );

        JavaPairDStream<String, String> pairDS = directStream.mapToPair(
            new PairFunction<ConsumerRecord<String, String>, String, String>() {
                @Override
                public Tuple2<String, String> call(ConsumerRecord<String, String> record) throws Exception {
                    return new Tuple2<>(record.key(), record.value());
                }
            }
        );

        pairDS.print();

        jsc.start();
        jsc.awaitTermination();
    }
}
