package exception

import kotlin.reflect.KClass

/**
 * *Custom Exception*
 *
 * This class represents a custom exception that is thrown when there is a class cast exception
 * for a JsonValue.
 *
 * @property clazz The class that caused the exception.
 */
class JsonValueClassCastException(val clazz: KClass<*>) : Exception() {
    override val message: String?
    /**
     * Get the message of the exception.
     *
     * @return The message of the exception.
     */
    get() = "JsonValue Class cast Exception : ${clazz.simpleName}"
}