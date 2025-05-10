package model

import visitor.JsonVisitor

sealed interface JsonValue {
    fun asJson(): String
    fun accept(visitor: JsonVisitor)
}