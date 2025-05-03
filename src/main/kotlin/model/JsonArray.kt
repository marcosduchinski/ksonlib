package model

import visitor.JsonVisitor

data class JsonArray(val elements: MutableList<JsonValue>) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>): R {
        elements.forEach { it.accept(visitor) }
        return visitor.visit(this)
    }

    override fun asJson(): String {
        return elements.joinToString(prefix = "[", postfix = "]", separator = ",") {
            it.asJson()
        }
    }

    fun map(transform: (JsonValue) -> JsonValue): JsonArray {
        val newElements = elements.map { transform(it)}.toMutableList()
        return JsonArray(newElements)
    }

    fun filter(predicate: (JsonValue) -> Boolean): JsonArray {
        val filteredElements = elements.filter(predicate).toMutableList()
        return JsonArray(filteredElements)
    }
}
