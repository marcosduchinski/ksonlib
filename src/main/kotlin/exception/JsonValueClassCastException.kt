package exception

import kotlin.reflect.KClass

/**
 * Custom exception thrown when a [JsonValue] cannot be cast to the expected type.
 *
 * @property clazz The Kotlin class that caused the cast exception.
 */
class JsonValueClassCastException(val clazz: KClass<*>) : Exception() {
    /**
     * The detail message string of this exception.
     */
    override val message: String?
        get() = "JsonValue Class cast Exception : ${clazz.simpleName}"
}