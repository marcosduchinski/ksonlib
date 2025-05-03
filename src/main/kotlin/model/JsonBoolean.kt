package model

import visitor.JsonVisitor

data class JsonBoolean(val value: Boolean) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>) = visitor.visit(this)
    override fun asString() = value.toString()
}
