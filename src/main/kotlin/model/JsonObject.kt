package model

import visitor.JsonVisitor

/**
 * Represents a JSON object.
 *
 * A JSON object is a collection of key-value pairs where keys are strings
 * and values are instances of [JsonValue]. This class provides functionality
 * to serialize the object and to perform operations such as filtering.
 *
 * @property members A map containing string keys and their corresponding [JsonValue]s.
 */
data class JsonObject(val members: Map<String, JsonValue>) : JsonValue {

    /**
     * Secondary constructor that creates a [JsonObject] from a vararg of key-value pairs.
     *
     * @param pairs Pairs of strings and [JsonValue]s to initialize the object with.
     */
    constructor(vararg pairs: Pair<String, JsonValue>) : this(mutableMapOf(*pairs))

    /**
     * Accepts a [JsonVisitor] and dispatches the visitor to this [JsonObject] and its values.
     *
     * @param visitor The visitor that processes this JSON object and its member values.
     */
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
        members.forEach { it.value.accept(visitor) }
    }

    /**
     * Retrieves the [JsonValue] associated with the given key.
     *
     * @param key The key of the member to retrieve.
     * @return The associated [JsonValue], or `null` if the key does not exist.
     */
    fun get(key: String): JsonValue? = members[key]

    /**
     * Serializes the JSON object to a JSON-formatted string.
     *
     * @return A string representing this JSON object.
     */
    override fun asJson(): String {
        return members.entries.joinToString(prefix = "{", postfix = "}", separator = ",") {
            "\"${it.key}\":${it.value.asJson()}"
        }
    }

    /**
     * Returns a new [JsonObject] containing only the entries that match the given predicate.
     *
     * @param predicate A function that returns `true` for entries to include.
     * @return A filtered [JsonObject] based on the provided predicate.
     */
    fun filter(predicate: (Map.Entry<String, JsonValue>) -> Boolean): JsonObject {
        val filteredElements = members.filter(predicate).toMutableMap()
        return JsonObject(filteredElements)
    }
}
