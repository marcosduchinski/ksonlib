package model

import visitor.JsonVisitor

class JsonNull() : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>) = visitor.visit(this)
    override fun asString() = "null"
}
