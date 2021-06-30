package com.gbl.spark.streaming

import org.apache.spark.sql.{DataFrame, SparkSession}

object ReadCSVTest {
    def main(args: Array[String]): Unit = {
        val reader: SparkSession = SparkSession.builder()
                .appName("CSV Reader")
                .master("local")
                .getOrCreate()
        reader.sparkContext.setLogLevel("ERROR")

        val path = "spark/datas/csv/civic_info.csv"

        val civic_info: DataFrame = reader.read.format("csv")
                .option("delimiter", ",") // 分隔符，看你具体是啥, 有的可能是|
                .option("header", "true") // 是否有头部，会自动帮你处理
                .option("nullValue", "\\N") // 空值替换成什么
                .option("inferSchema", "true") // 启用推断模式
                .load(path) // 其实应该存到hdfs或S3上, 从hdfs或S3上拿会比较好

        civic_info.show()
//        civic_info.printSchema()
        civic_info.createTempView("civic")

        val path2 = "spark/datas/csv/ticket_info.csv"
        val ticket_info: DataFrame = reader.read.format("csv")
                .option("delimiter", ",")
                .option("header", "true")
                .option("nullValue", "\\N")
                .option("inferSchema", "true")
                .load(path2)

        ticket_info.show()
//        civic_info.printSchema()
        ticket_info.createTempView("ticket")


        println("湖北籍人员信息如下: ")
        reader.sql("select id_no, name from civic where province = '湖北'").show()

        println("来自武汉疫区人员如下:")
        reader.sql("select id_no, name from civic where city = '武汉'").show()

        println("需要对员工进行隔离观察14天的公司: ")
        reader.sql("select distinct working_company from civic where province = '湖北'").show()

        println("有感染风险的车厢为: ")
        reader.sql("select distinct carriage_no from ticket where departure = '武汉'").show()

        println("需要执行隔离的人员: ")
        reader.sql("select passenger_name, passenger_id from ticket where carriage_no in (select distinct carriage_no from ticket where departure = '武汉')").show()

    }

}
