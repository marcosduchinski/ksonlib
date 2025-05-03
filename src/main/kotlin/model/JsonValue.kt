package model

import visitor.JsonVisitor

sealed interface JsonValue {
    fun asJson(): String
    fun <R> accept(visitor: JsonVisitor<R>): R
}