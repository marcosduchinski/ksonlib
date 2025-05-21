package model

import visitor.JsonVisitor

/**
 * *Interface*
 *
 * A sealed interface representing any valid JSON value.
 *
 * According to the JSON specification ([json.org](https://www.json.org/json-en.html)),
 * a JSON value can be one of the following:
 * - [JsonObject]
 * - [JsonArray]
 * - [JsonString]
 * - [JsonNumber]
 * - [JsonBoolean]
 * - [JsonNull]
 *
 * This interface is sealed to allow exhaustive when-expressions when working with known implementations.
 *
 * @see https://kotlinlang.org/docs/sealed-classes.html
 */
sealed interface JsonValue {

    /**
     * Converts this [JsonValue] to its JSON string representation.
     *
     * @return A JSON-compliant string.
     */
    fun asJson(): String

    /**
     * Accepts a [JsonVisitor] to perform operations based on the Visitor pattern.
     *
     * @param visitor The visitor that will process this JSON value.
     */
    fun accept(visitor: JsonVisitor)
}