package exception

class JsonValueClassCastException(val value: Any) : Exception() {
    override val message: String?
    get() = "JsonValue Class cast Exception : ${value::class}"
}