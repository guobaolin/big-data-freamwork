package com.gbl.hello.scala.case1

object Do {
    def main(args: Array[String]): Unit = {
        val link = new Link[String]("guo", _)
    }

    def show(x: Option[String]) = x match {
        case Some(value) => value
        case None => "?"
    }

    def show2: String => String = {
        case v@"France" => "Paris"
        case v@"Japan" => "Tokyo"
    }


    def describe(e: Expr): String = (e: @unchecked) match {
        case Number(_) => "a number"
        case Var(_) => "a variable"
        case _ => null
    }

    def append[T](xs: List[T], ys: List[T]): List[T] = {
        xs match {
            case Nil => ys
            case x :: xsl => x :: append(xsl, ys)
        }
    }

    def add(x:Int, y:Int) = {
        x+y
    }
}

