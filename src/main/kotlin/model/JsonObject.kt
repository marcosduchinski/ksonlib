package model

import visitor.JsonVisitor

data class JsonObject (val members: MutableMap<String, JsonValue>) : JsonValue {

    constructor(vararg pairs: Pair<String, JsonValue>) : this(mutableMapOf(*pairs))

    override fun accept(visitor: JsonVisitor) {
        members.forEach { it.value.accept(visitor) }
        return visitor.visit(this)
    }

    fun get(key: String): JsonValue? {
        members[key]?.let { return it }
        return null
    }

    override fun asJson(): String {
        return members.entries.joinToString(prefix = "{", postfix = "}", separator = ",") {
            "\"${it.key}\":${it.value.asJson()}"
        }
    }

    fun filter(predicate: (Map.Entry<String, JsonValue>) -> Boolean): JsonObject {
        val filteredElements = members.filter(predicate).toMutableMap()
        return JsonObject(filteredElements)
    }

}
