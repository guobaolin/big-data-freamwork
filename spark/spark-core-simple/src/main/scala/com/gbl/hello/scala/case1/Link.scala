package com.gbl.hello.scala.case1

class Link[+T](val head: T, val tail:Link[T]){
    def prepend[U >: T](newHead: U): Link[U] = new Link(newHead, this)
}
