package com.github.azihsoyn.ktformat

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object KTFormatSpec: Spek({
    given("string"){
        val str = "{greet}, {name}"
        on("both supplyed"){
            val formatted = str.format(mapOf("greet" to "hello", "name" to "world"))
            it("should return the result of greet and name replaced"){
                formatted shouldEqual "hello, world"
            }
        }

        on("only one supplyed"){
            val formatted = str.format(mapOf("greet" to "hey"))
            it("should return the result of only greet replaced"){
                formatted shouldEqual "hey, {name}"
            }
        }
    }

})