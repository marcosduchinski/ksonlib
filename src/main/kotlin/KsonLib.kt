import model.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

class KsonLib(val obj: Any?) {

    private fun KClass<*>.matchProperty(parameter: KParameter): KProperty<*> {
        return declaredMemberProperties.first { it.name == parameter.name }
    }

    private fun mapType(type: KType, value: Any?): JsonValue {
        if (value == null) {
            return JsonNull()
        }
        when (type.classifier) {
            String::class -> return JsonString(value.toString())
            Boolean::class -> return JsonBoolean(value as Boolean)
            Int::class -> return JsonNumber(value as Int)
            Double::class -> return JsonNumber(value as Double)
            Enum::class -> return JsonString(value.toString())
            else -> TODO("Unsupported type ${type}")
        }
    }

    fun asJson(): String {
        val clazz = obj!!::class
        val members = mutableMapOf<String, JsonValue>()
        clazz.primaryConstructor?.parameters?.forEach { p ->
            val prop = clazz.matchProperty(p)
            val jsonValue = mapType(prop.returnType, prop.call(obj))
            members[prop.name] = jsonValue
        }
        return JsonObject(members).asJson()
    }
}