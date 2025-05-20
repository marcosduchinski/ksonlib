package exception

/**
 * *Custom Exception*
 *
 * This class represents a custom exception that is thrown when is not possible to generate a valid JsonValue
 *
 * @property customMessage The custom message that describes the validation error.
 */
class JsonValueValidationException(val customMessage: String) : Exception() {
    override val message: String?/**
     * Get the message of the exception.
     *
     * @return The message of the exception.
     */
    get() = "JsonValue not valid: ${customMessage}"
}
