package com.gbl.bigdata.hadoop.sortdemo;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author guobaolin
 * @date 2021/2/26
 * @Description: Reducer
 */
public class SortReducer extends Reducer<SortBean, NullWritable, SortBean, NullWritable> {
    @Override
    protected void reduce(SortBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key, NullWritable.get());
    }
}
