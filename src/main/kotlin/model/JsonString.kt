package model

import visitor.JsonVisitor

data class JsonString(val value: String) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>) = visitor.visit(this)
    override fun asJson() = "\"" + value + "\""
}