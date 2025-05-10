package model

import visitor.JsonVisitor

data class JsonArray(val elements: MutableList<JsonValue>) : JsonValue {

    constructor(vararg elements: JsonValue) : this(mutableListOf(*elements))

    override fun accept(visitor: JsonVisitor) {
        elements.forEach { it.accept(visitor) }
        visitor.visit(this)
    }

    fun get(index: Int): JsonValue? {
        return elements.get(index)
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
