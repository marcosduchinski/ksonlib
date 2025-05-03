package http

import kotlin.reflect.KClass

//https://kotlinlang.org/docs/functions.html#variable-number-of-arguments-varargs
class GetJson (vararg clazz : KClass<*>) {

    init {
        for (clazz in clazz) {
            println("Loading ${clazz.simpleName}")
        }
    }

    fun start(port : Int) {
        println("Start listening at port $port")
    }
}

fun main() {
    val app = GetJson(Controller::class)
    app.start(8080)
}