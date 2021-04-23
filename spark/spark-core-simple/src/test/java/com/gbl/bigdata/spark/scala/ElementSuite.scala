package com.gbl.bigdata.spark.scala

import org.scalatest.FunSuite


class ElementSuite extends FunSuite {

    test("elem result should have passed width") {
        val caught = intercept[ArithmeticException] {
            1 / 0
        }
        assert(caught.getMessage == "/ by zero")
    }

}
