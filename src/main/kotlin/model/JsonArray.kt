package model

import visitor.JsonVisitor

data class JsonArray(val elements: MutableList<JsonValue>) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>): R {
        elements.forEach { it.accept(visitor) }
        return visitor.visit(this)
    }

    override fun asString(): String {
        return elements.joinToString(prefix = "[", postfix = "]", separator = ",") {
            it.asString()
        }
    }

    fun map(transform: (JsonValue) -> JsonValue) {
        TODO()
    }
}
