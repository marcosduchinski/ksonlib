package exception

class UnsupportedJsonValueType(val value: Any) : Exception() {
    override val message: String?
    get() = "Not supported type to JsonValue: ${value::class.simpleName}"
}