package com.gbl.bigdata.hadoop.sortdemo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author guobaolin
 * @date 2021/2/26
 * @Description: 自定义类型和比较器
 */
public class SortBean implements WritableComparable<SortBean> {

    private String word;
    private int num;

    @Override
    public String toString() {
        return  word + "\t" + num;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int compareTo(SortBean sortBean) {
        int result = this.word.compareTo(sortBean.word);
        if (result == 0) {
            return num - sortBean.num;
        }
        return result;
    }

    // 实现序列化
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(word);
        dataOutput.writeInt(num);
    }

    // 实现反序列化
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.word = dataInput.readUTF();
        this.num = dataInput.readInt();
    }
}
