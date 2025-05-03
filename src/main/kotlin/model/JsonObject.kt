package model

import visitor.JsonVisitor

data class JsonObject(val members: MutableMap<String, JsonValue>) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>): R {
        members.forEach { it.value.accept(visitor) }
        return visitor.visit(this)
    }

    override fun asString(): String {
        return members.entries.joinToString(prefix = "{", postfix = "}", separator = ",") {
            "\"${it.key}\": ${it.value.asString()}"
        }
    }

    fun map(transform: (JsonValue) -> JsonValue) {
        TODO()
    }

    fun filter(predicate: (JsonValue) -> Boolean): JsonValue {
        TODO()
    }
}
