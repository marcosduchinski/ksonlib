package model

import visitor.JsonVisitor

data class JsonNumber(val value: Number) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>) = visitor.visit(this)
    override fun asString() = value.toString()
}
