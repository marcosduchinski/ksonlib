import model.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor


class KsonLib(val obj: Any?) {

    private fun KClass<*>.matchProperty(parameter: KParameter): KProperty<*> {
        return declaredMemberProperties.first { it.name == parameter.name }
    }


    fun mapType(value: Any?): JsonValue {
        if (value == null) {
            return JsonNull()
        } else if (value.javaClass.isEnum) {
            return JsonString(value.toString())
        } else if (value is Int) {
            return JsonNumber(value)
        } else if (value is Double) {
            return JsonNumber(value)
        } else if (value is String) {
            return JsonString(value)
        } else if (value is Boolean) {
            return JsonBoolean(value)
        } else if (value::class.isData) {
            val clazz = value::class
            val members = mutableMapOf<String, JsonValue>()
            clazz.primaryConstructor?.parameters?.forEach { p ->
                val prop = clazz.matchProperty(p)
                val jsonValue = mapType(prop.call(value))
                members[prop.name] = jsonValue
            }
            return JsonObject(members)
        } else if (value is List<*>) {
            val jsonArray = JsonArray(mutableListOf())
            value.forEach { item ->
                val jsonValue = mapType(item)
                jsonArray.elements.add(jsonValue)
            }
            return jsonArray
        } else if (value is Map<*, *>) {
            val jsonObject = JsonObject(mutableMapOf())
            value.forEach { (key, item) ->
                val jsonValue = mapType(item)
                jsonObject.members[key.toString()] = jsonValue
            }
            return jsonObject
        } else {
            TODO("Unsupported type: ${value::class.simpleName}")
        }

    }

    fun asJson(): String {
        return mapType(obj).asJson()
    }
}