package model

import visitor.JsonVisitor

/**
 * Represents a JSON boolean value.
 *
 * This class wraps a [Boolean] and provides methods to serialize it to JSON format
 * and to accept a visitor as part of the Visitor design pattern.
 *
 * @property value The boolean value represented as a JSON value.
 */
data class JsonBoolean(val value: Boolean) : JsonValue {

    /**
     * Accepts a [JsonVisitor] and dispatches the visitor to this [JsonBoolean] instance.
     *
     * @param visitor The visitor that processes this JSON boolean value.
     */
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)

    /**
     * Serializes the boolean value to a JSON-compliant string.
     *
     * @return A string representation of the boolean value, either "true" or "false".
     */
    override fun asJson() = value.toString()
}