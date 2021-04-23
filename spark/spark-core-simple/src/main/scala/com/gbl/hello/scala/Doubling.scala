package com.gbl.hello.scala

trait Doubling extends IntQueue {

    def get() = {
        println("doubling get")
    }

//    abstract override def put(x: Int): Unit = {
//        println("Doubling put")
//        super.put(2 * x)
//    }
}
