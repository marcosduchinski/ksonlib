package model

import visitor.JsonVisitor

object JsonNull : JsonValue {
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)
    override fun asJson() = "null"
}
