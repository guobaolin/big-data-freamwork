package edu.model

import org.apache.spark.sql.SparkSession

object ALSModeling {
    def main(args: Array[String]): Unit = {
        // 1. 环境准备
        val spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("ALSModeling")
                .config("spark.local.dir", "temp")
                .config("spark.sql.shuffle.partitions", "4")
                .getOrCreate()
        spark.sparkContext.setLogLevel("WARN")
        import spark.implicits._

        // 2. 加载数据并转换为：DataSet[Rating(学生Id，问题Id，推荐指数)]
        val path = "data/output/question_info.json"
        spark.sparkContext.textFile(path)
                .map(parseAnswerInfo)
                .toDS()
                .cache()
        //TODO
    }

}
