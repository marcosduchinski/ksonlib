package model

import visitor.JsonVisitor

data class JsonObject(val members: MutableMap<String, JsonValue>) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>): R {
        members.forEach { it.value.accept(visitor) }
        return visitor.visit(this)
    }

    override fun asJson(): String {
        return members.entries.joinToString(prefix = "{", postfix = "}", separator = ",") {
            "\"${it.key}\": ${it.value.asJson()}"
        }
    }

    fun filter(predicate: (Map.Entry<String, JsonValue>) -> Boolean): JsonObject {
        val filteredElements = members.filter(predicate).toMutableMap()
        return JsonObject(filteredElements)
    }

}
