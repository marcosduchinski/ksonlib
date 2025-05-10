package exception

class JsonValueValidationException(val customMessage: String) : Exception() {
    override val message: String?
    get() = "JsonValue not valid: ${customMessage}"
}