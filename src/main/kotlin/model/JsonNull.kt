package model

import visitor.JsonVisitor

/**
 * Represents a JSON null value.
 *
 * This is a singleton object used to represent the `null` keyword in JSON.
 * It provides methods for serialization and visitor pattern support.
 */
object JsonNull : JsonValue {

    /**
     * Accepts a [JsonVisitor] and dispatches the visitor to this [JsonNull] instance.
     *
     * @param visitor The visitor that processes this JSON null value.
     */
    override fun accept(visitor: JsonVisitor) = visitor.visit(this)

    /**
     * Serializes the JSON null value to its JSON string representation.
     *
     * @return The string `"null"`.
     */
    override fun asJson() = "null"
}
