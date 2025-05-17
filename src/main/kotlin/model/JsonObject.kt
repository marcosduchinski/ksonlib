package model

import visitor.JsonVisitor

data class JsonObject (val members: Map<String, JsonValue>) : JsonValue {

    constructor(vararg pairs: Pair<String, JsonValue>) : this(mutableMapOf(*pairs))

    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
        members.forEach { it.value.accept(visitor) }
    }

    fun get(key: String): JsonValue? = members[key]

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
