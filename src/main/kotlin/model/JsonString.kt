package model

import visitor.JsonVisitor

/**
 * Represents a JSON string value.
 *
 * This class wraps a [String] and provides functionality to serialize it
 * to a JSON-compliant string and accept visitors.
 *
 * @property value The raw string value of the JSON string.
 */
data class JsonString(val value: String) : JsonValue {

    /**
     * Accepts a [JsonVisitor] and dispatches the visitor to this [JsonString] instance.
     *
     * @param visitor The visitor that processes this JSON string value.
     */
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)

    /**
     * Serializes the string value into a JSON-compliant string.
     *
     * @return The string enclosed in double quotes, as per JSON specification.
     */
    override fun asJson() = "\"" + value + "\""
}