package exception

import kotlin.reflect.KClass

/**
 * Custom exception thrown when a type is not supported for conversion to a [JsonValue].
 *
 * @property clazz The Kotlin class of the unsupported type.
 */
class JsonValueUnsupportedTypeException(val clazz: KClass<*>) : Exception() {
    /**
     * The detail message string of this exception.
     */
    override val message: String?
        get() = "Not supported type to JsonValue: ${clazz.simpleName}"
}