package com.gbl.hello.scala

trait Philosophical extends Object{

    def philosophize() = {
        println("I consume memory, therefore I am!")
    }

    abstract override def toString(): String = {
        println("Philosophical toString")
        super.toString
    }

}
