import kotlin.io.encoding.Base64
import kotlin.reflect.KClass

/*
https://www.json.org/json-en.html
https://datatracker.ietf.org/doc/html/rfc8259
Goal: Develop classes to represent JSON values (model),
allowing manipulation operations and serialization to strings (standard format).

value
├── object
│   └── { members }
│       └── pair (name: value)
│           ├── string (as name)
│           └── value (recursive)
├── array
│   └── [ elements ]
│       └── value (recursive)
├── string
├── number
├── true
├── false
└── null
 */
sealed interface JsonValue {
    fun asString(): String
    fun <R> accept(visitor : JsonVisitor<R>) : R

}

interface JsonVisitor<R> {
    fun visitJsonString(value: JsonString) : R
    fun visitJsonNumber(value: JsonNumber) : R
    fun visitJsonBoolean(value: JsonBoolean) : R
    fun visitJsonNull(value: JsonNull) : R
    fun visitJsonArray(value: JsonArray) : R
    fun visitJsonObject(value: JsonObject) : R
}

data class JsonString(val value: String) : JsonValue {
    override fun <R> accept(visitor : JsonVisitor<R>) = visitor.visitJsonString(this)
    override fun asString() =  "not implemented yet"
}
data class JsonNumber(val value: Number) : JsonValue {
    override fun <R> accept(visitor : JsonVisitor<R>) = visitor.visitJsonNumber(this)
    override fun asString() =  "not implemented yet"
}
data class JsonBoolean(val value: Boolean) : JsonValue {
    override fun <R> accept(visitor : JsonVisitor<R>) = visitor.visitJsonBoolean(this)
    override fun asString() =  value.toString()
}
data class JsonNull(val value: Boolean) : JsonValue {
    override fun <R> accept(visitor : JsonVisitor<R>) = visitor.visitJsonNull(this)
    override fun asString() =  "null"
}

data class JsonArray(val elements: MutableList<JsonValue>) : JsonValue {
    override fun <R> accept(visitor : JsonVisitor<R>) = visitor.visitJsonArray(this)
    override fun asString() =  "not implemented yet"
    fun add(element: JsonValue) = elements.add(element)
    fun map(transform: (JsonValue) -> JsonValue){
        TODO()
    }
}

data class JsonObject(val members: MutableMap<JsonString,JsonValue >) : JsonValue {
    override fun <R> accept(visitor : JsonVisitor<R>) = visitor.visitJsonObject(this)
    override fun asString() =  "not implemented yet"
    fun put(key: JsonString, value: JsonValue) = members.put(key, value)
    fun remove(key: JsonString) = members.remove(key)
    fun map(transform: (JsonValue) -> JsonValue){
        TODO()
    }
    fun filter(predicate: (JsonValue) -> Boolean) : JsonValue {
        TODO()
    }
}


fun serialize(clazz : KClass<*>): String {
    //iterate over members (properties and values)
    return JsonObject(members = mutableMapOf()).asString();
}




