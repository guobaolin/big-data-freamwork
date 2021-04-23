package com.gbl.bigdata.hadoop.combinerdemo;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author guobaolin
 * @date 2021/2/26
 * @Description: mapper
 */
public class CombinerMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        Text text = new Text();
        LongWritable longWritable = new LongWritable();
        String[] split = value.toString().split(",");

        for (String s : split) {
            text.set(s);
            longWritable.set(1);
            context.write(text, longWritable);
        }
    }
}
