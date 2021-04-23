package com.gbl.hello.scala

import scala.collection.mutable.ArrayBuffer

class BasicIntQueue extends IntQueue {
    private val buf = new ArrayBuffer[Int]

    def get(): Unit = buf.remove(0)

    def put(x: Int): Unit = {
        println("BasicIntQueue put")
        buf += x
    }

}
