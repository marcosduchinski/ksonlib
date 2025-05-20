package model

import visitor.JsonVisitor

/**
 *
 *
 * @property value The numeric value of the JSON number.
 */
data class JsonNumber(val value: Number) : JsonValue {
    /*
        * Accepts a visitor and calls the appropriate visit method for this JsonNumber.
     */
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)
    override fun asJson() = value.toString()
    fun asInt() = value.toInt()
    fun asLong() = value.toLong()
    fun asFloat() = value.toFloat()
    fun asDouble() = value.toDouble()
}