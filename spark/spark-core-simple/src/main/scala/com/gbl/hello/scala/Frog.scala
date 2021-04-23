package com.gbl.hello.scala

class Frog extends Object with Philosophical {

    override def toString(): String = {
        "Frog"
    }

}


object Frog {

    def main(args: Array[String]): Unit = {
        val frog = new Frog
        println(frog.toString())
    }
}
