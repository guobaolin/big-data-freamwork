package com.gbl.bigdata.flink;

import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author guobaolin
 * @date 2021/5/13
 * @Description: 流处理 word count
 */
public class StreamWordCount {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从文本中读取数据
        String inputPath = "D:\\github\\sf\\big-data-freamwork\\flink\\flink-core-simple\\src\\main\\resources\\hello.txt";
    }
}
