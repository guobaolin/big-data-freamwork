package com.gbl.bigdata.hadoop.sortdemo;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author guobaolin
 * @date 2021/2/26
 * @Description: Map
 */
public class SortMapper extends Mapper<LongWritable, Text, SortBean, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 将行文本数据（V1)拆分，并将数据封装到 SortBean 对象，就可以得到K2
        String[] split = value.toString().split("\t");

        SortBean sortBean = new SortBean();
        sortBean.setWord(split[0]);
        sortBean.setNum(Integer.parseInt(split[1]));

        // 将K2 V2 写入上下文中
        context.write(sortBean, NullWritable.get());

    }
}
