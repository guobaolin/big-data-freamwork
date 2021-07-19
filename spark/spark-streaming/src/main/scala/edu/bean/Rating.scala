package edu.bean

/**
 * 学生题目推荐指数样例类
 *
 * @param student_id  学生Id
 * @param question_id 题目Id
 * @param rating      推荐指数
 */
case class Rating(
                         student_id: String,
                         question_id: String,
                         rating: Float
                 ) extends Serializable
