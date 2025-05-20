package model

import visitor.JsonVisitor

/**
 * *Interface*
 *
 * This interface represents a JsonValue.
 * Following the JSON built definition, available in https://www.json.org/json-en.html,
 * a JsonValue can be:
 * - A JsonObject
 * - A JsonArray
 * - A JsonString
 * - A JsonNumber
 * - A JsonBoolean
 * - A JsonNull
 *  @see https://kotlinlang.org/docs/sealed-classes.html
 */
sealed interface JsonValue {
    /**
     * Convert the JsonValue to a JSON string representation.
     *
     * @return The JSON string representation of the JsonValue.
     */
    fun asJson(): String
    /**
     * Accept a visitor to perform operations on the JsonValue.
     *
     * @param visitor The visitor to accept.
     */
    fun accept(visitor: JsonVisitor)
}