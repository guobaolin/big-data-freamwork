package com.gbl.bigdata.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author guobaolin
 * @date 2021/5/13
 * @Description: 批处理word count
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
        // 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 从文本中读取数据
        String inputPath = "D:\\github\\sf\\big-data-freamwork\\flink\\flink-core-simple\\src\\main\\resources\\hello.txt";
        DataSource<String> dataSource = env.readTextFile(inputPath);

        // 对数据集进行处理，按空格分词展开
        DataSet<Tuple2<String, Integer>> resultSet = dataSource.flatMap(new MyFlatMapper())
            .groupBy(0)
            .sum(1);
        resultSet.print();
    }

    // 自定义类，实现FlatMapFunction接口
    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
            // 按空格分词
            String[] words = value.split(" ");
            // 遍历所有word， 包成二元组输出
            for (String word : words) {
                collector.collect(new Tuple2<>(word, 1));
            }
        }
    }
}
