package com.gbl.bigdata.spark


object Demo1 {

    def main(args: Array[String]): Unit = {
        val firstArg: String = if (!args.isEmpty) args(0) else ""
        val friend: Any = firstArg match {
            case "aa" => "hello"
            case "bb" => 2
            case "cc" => false
            case _ => new Rational(1, 2)
        }


        val str: String = firstArg match {
            case "salt" => "pepper"
            case "chips" => "salsa"
            case "eggs" => "bacon"
            case _ => "huh?"
        }
    }

}
