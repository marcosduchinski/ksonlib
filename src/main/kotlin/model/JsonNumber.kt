package model

import visitor.JsonVisitor

/**
 * JsonNumber is a data class that represents a JSON number value.
 *
 * @property value The numeric value of the JSON number.
 */
data class JsonNumber(val value: Number) : JsonValue {
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)
    override fun asJson() = value.toString()
    fun asInt() = value.toInt()
    fun asLong() = value.toLong()
    fun asFloat() = value.toFloat()
    fun asDouble() = value.toDouble()
}