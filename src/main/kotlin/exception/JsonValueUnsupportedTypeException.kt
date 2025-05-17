package exception

import kotlin.reflect.KClass

/**
 * *Custom Exception*
 *
 * This class represents a custom exception that is thrown when there is an unsupported type for a JsonValue.
 *
 * @property clazz The class which type is not supported.
 * */
class JsonValueUnsupportedTypeException(val clazz: KClass<*>) : Exception() {
    override val message: String?
    /**
     * Get the message of the exception.
     *
     * @return The message of the exception.
     */
    get() = "Not supported type to JsonValue: ${clazz.simpleName}"
}