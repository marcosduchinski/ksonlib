package model

import visitor.JsonVisitor

data class JsonString(val value: String) : JsonValue {
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)
    override fun asJson() = "\"" + value + "\""
}