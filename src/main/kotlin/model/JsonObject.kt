package model

import visitor.JsonVisitor

/**
 * JsonObject is a data class that represents a JSON object
 *
 * @property members The map with pairs of string keys and JsonValue values.
 *
 */
data class JsonObject (val members: Map<String, JsonValue>) : JsonValue {

    constructor(vararg pairs: Pair<String, JsonValue>) : this(mutableMapOf(*pairs))

    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
        members.forEach { it.value.accept(visitor) }
    }

    fun get(key: String): JsonValue? = members[key]

    /**
     * Converts the JsonObject to a JSON string representation.
     *
     * @return A string representing the JSON object.
     */
    override fun asJson(): String {
        return members.entries.joinToString(prefix = "{", postfix = "}", separator = ",") {
            "\"${it.key}\":${it.value.asJson()}"
        }
    }

    /**
     * Maps each entry in the JsonObject to a new JsonObject using the provided transform function.
     */
    fun filter(predicate: (Map.Entry<String, JsonValue>) -> Boolean): JsonObject {
        val filteredElements = members.filter(predicate).toMutableMap()
        return JsonObject(filteredElements)
    }

}
