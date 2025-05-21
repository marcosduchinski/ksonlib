import exception.JsonValueClassCastException
import exception.JsonValueUnsupportedTypeException
import model.*
import visitor.ValidationVisitor
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

/**
 * KsonLib is a library for serializing Kotlin objects to JSON format.
 * It supports the following types: Int, Double, String, Boolean, List, Map, and custom data classes.
 * Other types are not supported yet and will throw an exception.
 *
 * @property obj The object to be serialized to JSON.
 */
class KsonLib(val obj: Any? = null) {

    /**
     * Matches a Kotlin class property to a constructor parameter by name.
     *
     * @param parameter The constructor parameter to match.
     * @return The corresponding property in the Kotlin class.
     */
    private fun KClass<*>.matchProperty(parameter: KParameter): KProperty<*> {
        return declaredMemberProperties.first { it.name == parameter.name }
    }

    /**
     * Maps a Kotlin value to its corresponding JSON value representation.
     *
     * Supports null, enum, primitive types, lists, maps, and data classes.
     *
     * @param value The value to be mapped.
     * @return The mapped [JsonValue].
     * @throws JsonValueUnsupportedTypeException if the value's type is unsupported.
     */
    private fun mapType(value: Any?): JsonValue = when {
        value == null -> JsonNull
        value.javaClass.isEnum -> JsonString(value.toString())
        value is Int -> JsonNumber(value)
        value is Double -> JsonNumber(value)
        value is String -> JsonString(value)
        value is Boolean -> JsonBoolean(value)
        value is List<*> -> JsonArray(value.map { mapType(it) }.toMutableList())
        value is Map<*, *> -> JsonObject(value.entries.associate { (k, v) -> k.toString() to mapType(v) }.toMutableMap())
        value::class.isData && value !is JsonValue -> {
            val clazz = value::class
            val members = clazz.primaryConstructor?.parameters?.associate { param ->
                val prop = clazz.matchProperty(param)
                prop.name to mapType(prop.call(value))
            } ?: emptyMap()
            JsonObject(members.toMutableMap())
        }
        else -> throw JsonValueUnsupportedTypeException(value::class)
    }

    /**
     * Serializes the stored object to a JSON string.
     *
     * The object is first mapped to a JSON value and validated.
     *
     * @return The JSON string representation.
     */
    fun asJson(): String {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue.asJson()
    }

    /**
     * Serializes the stored object to a [JsonValue].
     *
     * The object is first mapped and validated.
     *
     * @return The root [JsonValue] representing the object.
     */
    fun asJsonValue(): JsonValue {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue
    }

    /**
     * Converts the stored object to a [JsonObject].
     *
     * Validates the object and casts the result to [JsonObject].
     *
     * @return The [JsonObject] representation.
     * @throws JsonValueClassCastException if the object is not a JSON object.
     */
    fun asJsonObject(): JsonObject {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue as? JsonObject ?: throw JsonValueClassCastException(jsonValue::class)
    }

    /**
     * Converts the stored object to a [JsonArray].
     *
     * Validates the object and casts the result to [JsonArray].
     *
     * @return The [JsonArray] representation.
     * @throws JsonValueClassCastException if the object is not a JSON array.
     */
    fun asJsonArray(): JsonArray {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue as? JsonArray ?: throw JsonValueClassCastException(jsonValue::class)
    }
}