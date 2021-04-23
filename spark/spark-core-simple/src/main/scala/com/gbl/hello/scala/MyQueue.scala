package com.gbl.hello.scala

class MyQueue extends Doubling {
//    override def get() = println("123")

    override def put(x: Int) = println("hello world")

}


object MyQueue{
    def main(args: Array[String]): Unit = {
        val queue = new MyQueue
        queue.get()
    }
}

