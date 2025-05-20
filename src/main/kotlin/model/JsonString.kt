package model

import visitor.JsonVisitor

/**
 * JsonString is a data class that represents a JSON string value.
 *
 * @property value The string value of the JSON string.
 */
data class JsonString(val value: String) : JsonValue {
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)
    override fun asJson() = "\"" + value + "\""
}