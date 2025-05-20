package model

import visitor.JsonVisitor

/**
 * JsonBoolean is a data class that represents a JSON boolean value.
 *
 * @property value The boolean to convert to JsonValue and serialize to JSON format string.
 */
data class JsonBoolean(val value: Boolean) : JsonValue {
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)
    override fun asJson() = value.toString()
}
