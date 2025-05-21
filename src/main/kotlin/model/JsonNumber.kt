package model

import visitor.JsonVisitor

/**
 * Represents a JSON number value.
 *
 * Wraps a [Number] and provides methods to serialize it to JSON format
 * as well as convert it to various numeric types.
 *
 * @property value The numeric value of the JSON number.
 */
data class JsonNumber(val value: Number) : JsonValue {

    /**
     * Accepts a [JsonVisitor] and dispatches the visitor to this [JsonNumber] instance.
     *
     * @param visitor The visitor that processes this JSON number value.
     */
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)

    /**
     * Serializes the numeric value to a JSON-compliant string.
     *
     * @return A string representation of the number.
     */
    override fun asJson() = value.toString()

    /**
     * Converts the number value to an [Int].
     *
     * @return The numeric value as an [Int].
     */
    fun asInt() = value.toInt()

    /**
     * Converts the number value to a [Long].
     *
     * @return The numeric value as a [Long].
     */
    fun asLong() = value.toLong()

    /**
     * Converts the number value to a [Float].
     *
     * @return The numeric value as a [Float].
     */
    fun asFloat() = value.toFloat()

    /**
     * Converts the number value to a [Double].
     *
     * @return The numeric value as a [Double].
     */
    fun asDouble() = value.toDouble()
}