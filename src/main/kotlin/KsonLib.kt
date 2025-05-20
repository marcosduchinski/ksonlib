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
 * It supports the following types: Int, Double, String, Boolean, List, Map, and custom data classes. Other types
 * are not supported yet and will throw an exception.
 *
 * @param obj The object to be serialized to JSON.
 */
class KsonLib(val obj: Any? = null) {

    private fun KClass<*>.matchProperty(parameter: KParameter): KProperty<*> {
        return declaredMemberProperties.first { it.name == parameter.name }
    }

    /**
     * Maps the given value to a JSON value.
     * It checks the type of the value and converts it to the corresponding JSON type.
     *
     * @param value The value to be mapped.
     * @return The mapped JSON value.
     * @throws JsonValueUnsupportedTypeException if the value type is not supported.
     */
    private fun mapType(value: Any?): JsonValue = when {
        value == null -> JsonNull
        value.javaClass.isEnum -> JsonString(value.toString())
        value is Int -> JsonNumber(value)
        value is Double -> JsonNumber(value)
        value is String -> JsonString(value)
        value is Boolean -> JsonBoolean(value)
        value is List<*> -> JsonArray(value.map {
            mapType(it)
        }.toMutableList())

        value is Map<*, *> -> JsonObject(value.entries.associate { (k, v) ->
            k.toString() to mapType(v)
        }.toMutableMap())

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
     * Serializes the object to JSON format.
     * It maps the object to a JSON value and validates it.
     *
     * @return The serialized JSON string.
     */
    fun asJson(): String {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue.asJson()
    }

    /**
     * Serializes the object to JSON format.
     * It maps the object to a JSON value and validates it.
     *
     * @return The JsonValue representation of the object.
     */
    fun asJsonValue(): JsonValue {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue
    }

    /**
     * Converts the object to a JsonObject.
     * It maps the object to a JSON value and validates it.
     *
     * @return The JsonObject representation of the object.
     * @throws JsonValueClassCastException if the JSON value is not a JsonObject.
     */
    fun asJsonObject(): JsonObject {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue as? JsonObject ?: throw JsonValueClassCastException(jsonValue::class)
    }

    /**
     * Converts the object to a JsonArray.
     * It maps the object to a JSON value and validates it.
     *
     * @return The JsonArray representation of the object.
     * @throws JsonValueClassCastException if the JSON value is not a JsonArray.
     */
    fun asJsonArray(): JsonArray {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue as? JsonArray ?: throw JsonValueClassCastException(jsonValue::class)
    }
}