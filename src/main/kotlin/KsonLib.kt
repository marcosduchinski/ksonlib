import model.*
import visitor.ValidationVisitor
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor


class KsonLib(val obj: Any? = null) {

    private fun KClass<*>.matchProperty(parameter: KParameter): KProperty<*> {
        return declaredMemberProperties.first { it.name == parameter.name }
    }

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
        value::class.isData -> {
            val clazz = value::class
            val members = clazz.primaryConstructor?.parameters?.associate { param ->
                val prop = clazz.matchProperty(param)
                prop.name to mapType(prop.call(value))
            } ?: emptyMap()
            JsonObject(members.toMutableMap())
        }
        else -> TODO("Unsupported type: ${value::class.simpleName}")
    }


    fun asJson(): String {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue.asJson()
    }

    fun asJsonValue(): JsonValue {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue
    }

    fun asJsonObject(): JsonObject {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue as JsonObject
    }

    fun asJsonArray(): JsonArray {
        val jsonValue = mapType(obj)
        jsonValue.accept(ValidationVisitor())
        return jsonValue as JsonArray
    }

}