package model

import visitor.JsonVisitor

data class JsonBoolean(val value: Boolean) : JsonValue {
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)
    override fun asJson() = value.toString()
}
