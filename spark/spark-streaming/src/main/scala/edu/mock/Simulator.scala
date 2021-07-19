package edu.mock

import java.text.SimpleDateFormat

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * 在线教育学生学习数据模拟程序
 */
object Simulator {

    // 模拟数据
    // 学生Id
    val arr1 = ArrayBuffer[String]()
    for (i <- 1 to 50) {
        arr1 += "学生ID_" + i
    }

    // 教材Id
    val arr2 = Array("教材ID_1", "教材ID_2")

    // 年级Id
    val arr3 = Array("年级ID_1", "年级ID_2", "年级ID_3", "年级ID_4", "年级ID_5", "年级ID_6")

    // 科目Id
    val arr4 = Array("科目ID_1_数学", "科目ID_2_语文", "科目ID_3_英语")

    // 章节Id
    val arr5 = Array("章节ID_chapter_1", "章节ID_chapter_2", "章节ID_chapter_3")

    // 题目Id与教材、年级、科目、章节的对应关系
    val questionMap = collection.mutable.HashMap[String, ArrayBuffer[String]]()

    var questionID = 1
    for (textbookID <- arr2; gradeID <- arr3; subjectID <- arr4; chapterID <- arr5) {
        val key = new StringBuilder()
                .append(textbookID).append("^")
                .append(gradeID).append("^")
                .append(subjectID).append("^")
                .append(chapterID)

        val questionArr = new ArrayBuffer[String]()
        for (i <- 1 to 20) {
            questionArr += "题目ID_" + questionID
            questionID += 1
        }
        questionMap.put(key.toString(), questionArr)
    }

    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    def getQuestion() = {
        // 随机教材ID
        val textbookIDRandom: String = arr2(Random.nextInt(arr2.length))
        // 随机年级ID
        val gradeIDRandom: String = arr3(Random.nextInt(arr3.length))
        // 随机科目ID
        val subjectIDRandom: String = arr4(Random.nextInt(arr4.length))
        // 随机章节ID
        val chapterIDRandom: String = arr5(Random.nextInt(arr5.length))

        val key = new StringBuilder()
                .append(textbookIDRandom).append("^")
                .append(gradeIDRandom).append("^")
                .append(subjectIDRandom).append("^")
                .append(chapterIDRandom)

        // 取出题目
        val questionArr: ArrayBuffer[String] = questionMap(key.toString())
        // 随机题目ID
        val questionIDRandom: String = questionArr(Random.nextInt(questionArr.length))

    }

}
