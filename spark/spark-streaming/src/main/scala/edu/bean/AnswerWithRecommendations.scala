package edu.bean

import java.sql.Timestamp

/**
 * 学生答题信息和推荐的题目ids样例类
 *
 * @param student_id      学生Id
 * @param textbook_id     教材Id
 * @param grade_id        年级Id
 * @param subject_id      科目Id
 * @param chapter_id      章节Id
 * @param question_id     题目Id
 * @param score           题目得分 0~10分
 * @param answer_time     答题提交时间， yy-MM-dd HH:mm:ss字符串
 * @param ts              答题提交时间，时间戳
 * @param recommendations 推荐的题目ids
 */
case class AnswerWithRecommendations(
                                            student_id: String,
                                            textbook_id: String,
                                            grade_id: String,
                                            subject_id: String,
                                            chapter_id: String,
                                            question_id: String,
                                            score: Int,
                                            answer_time: String,
                                            ts: Timestamp,
                                            recommendations: String
                                    ) extends Serializable
