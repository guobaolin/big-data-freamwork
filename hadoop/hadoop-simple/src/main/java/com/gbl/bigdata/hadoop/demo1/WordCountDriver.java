package com.gbl.bigdata.hadoop.demo1;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author guobaolin
 * @date 2021/2/25
 * @Description: 这个类就是mr程序运行时候的主类，本类中组装了一些程序运行时候所需要的信息
 */
public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();

        Job job = Job.getInstance();

        job.setJarByClass(WordCountDriver.class);

        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        String basePath = "/Users/guobaolin/Documents/project/person/github/big-data-freamwork";

//        String inputPath = basePath + "/hadoop/hadoop-simple/src/file/input/";
//        String outputPath = basePath + "/hadoop/hadoop-simple/src/file/output/";

        // hdfs path
        String inputPath = "hdfs://node01:8020/wordcount/input/";
        String outputPath = "hdfs://node01:8020/wordcount/output/";

        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        boolean b = job.waitForCompletion(true);

        System.exit(b ? 0 : 1);
    }
}
