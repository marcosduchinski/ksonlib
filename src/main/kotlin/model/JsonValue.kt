package model

import visitor.JsonVisitor

sealed interface JsonValue {
    fun asString(): String
    fun <R> accept(visitor: JsonVisitor<R>): R
}