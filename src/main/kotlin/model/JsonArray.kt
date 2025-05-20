package model

import visitor.JsonVisitor

/**
 * JsonArray represents a JSON array.
 *
 * @property elements The list of JSON values to serialize to JSON format list.
 *
 */
data class JsonArray(val elements: MutableList<JsonValue>) : JsonValue {

    constructor(vararg elements: JsonValue) : this(mutableListOf(*elements))

    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
        elements.forEach { it.accept(visitor) }
    }

    operator fun get(index: Int): JsonValue? {
        return elements.getOrNull(index)
    }

    override fun asJson(): String {
        return elements.joinToString(prefix = "[", postfix = "]", separator = ",") {
            it.asJson()
        }
    }

    fun map(transform: (JsonValue) -> JsonValue): JsonArray {
        val newElements = elements.map { transform(it) }.toMutableList()
        return JsonArray(newElements)
    }

    fun filter(predicate: (JsonValue) -> Boolean): JsonArray {
        val filteredElements = elements.filter(predicate).toMutableList()
        return JsonArray(filteredElements)
    }
}
