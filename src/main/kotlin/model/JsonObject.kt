package model

import visitor.JsonVisitor

data class JsonObject(val members: MutableMap<String, JsonValue>) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>): R {
        members.forEach { it.value.accept(visitor) }
        return visitor.visit(this)
    }

    override fun asString(): String {
        val json = StringBuilder()
        json.append("{")
        json.append(
            members.entries.joinToString(separator = ",") {
                "\"${it.key}\": ${it.value.asString()}"
            }
        )
        json.append("}")
        return json.toString()
    }

    fun put(key: String, value: JsonValue) = members.put(key, value)

    fun remove(key: String) = members.remove(key)

    fun map(transform: (JsonValue) -> JsonValue) {
        TODO()
    }

    fun filter(predicate: (JsonValue) -> Boolean): JsonValue {
        TODO()
    }
}
