package model

import visitor.JsonVisitor

/**
 * JsonNull is a singleton object that represents a JSON null value.
 */
object JsonNull : JsonValue {
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)
    override fun asJson() = "null"
}
