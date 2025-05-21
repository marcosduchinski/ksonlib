package exception

/**
 * Custom exception thrown when it is not possible to generate a valid [JsonValue].
 *
 * @property customMessage The message describing the validation error.
 */
class JsonValueValidationException(val customMessage: String) : Exception() {
    /**
     * The detail message string of this exception.
     */
    override val message: String?
        get() = "JsonValue not valid: ${customMessage}"
}
