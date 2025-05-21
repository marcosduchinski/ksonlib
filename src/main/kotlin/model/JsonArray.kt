package model

import visitor.JsonVisitor

/**
 * Represents a JSON array.
 *
 * Wraps a mutable list of [JsonValue] instances and provides methods
 * to manipulate and serialize them as a JSON array string.
 *
 * @property elements The list of [JsonValue] elements in the JSON array.
 */
data class JsonArray(val elements: MutableList<JsonValue>) : JsonValue {

    /**
     * Secondary constructor that allows initializing the array with a vararg of [JsonValue]s.
     *
     * @param elements The [JsonValue] elements to initialize the array with.
     */
    constructor(vararg elements: JsonValue) : this(mutableListOf(*elements))

    /**
     * Accepts a [JsonVisitor] and dispatches the visitor to this [JsonArray] and all its elements.
     *
     * @param visitor The visitor that processes this JSON array and its elements.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
        elements.forEach { it.accept(visitor) }
    }

    /**
     * Returns the element at the specified index, or `null` if the index is out of bounds.
     *
     * @param index The index of the element to retrieve.
     * @return The [JsonValue] at the specified index, or `null` if the index is invalid.
     */
    operator fun get(index: Int): JsonValue? {
        return elements.getOrNull(index)
    }

    /**
     * Serializes the JSON array into a JSON-compliant string.
     *
     * @return A JSON-formatted string representing the array.
     */
    override fun asJson(): String {
        return elements.joinToString(prefix = "[", postfix = "]", separator = ",") {
            it.asJson()
        }
    }

    /**
     * Returns a new [JsonArray] by applying the given transform function to each element.
     *
     * @param transform A function that transforms a [JsonValue] to another [JsonValue].
     * @return A new [JsonArray] with transformed elements.
     */
    fun map(transform: (JsonValue) -> JsonValue): JsonArray {
        val newElements = elements.map { transform(it) }.toMutableList()
        return JsonArray(newElements)
    }

    /**
     * Returns a new [JsonArray] containing only elements that match the given predicate.
     *
     * @param predicate A function that returns `true` for elements to include.
     * @return A new [JsonArray] with filtered elements.
     */
    fun filter(predicate: (JsonValue) -> Boolean): JsonArray {
        val filteredElements = elements.filter(predicate).toMutableList()
        return JsonArray(filteredElements)
    }
}
