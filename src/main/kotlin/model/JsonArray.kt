package model

import visitor.JsonVisitor

data class JsonArray(val elements: MutableList<JsonValue>) : JsonValue {
    override fun <R> accept(visitor: JsonVisitor<R>): R {
        elements.forEach { it.accept(visitor) }
        return visitor.visit(this)
    }

    override fun asString(): String {
        val sb = StringBuilder()
        sb.append("[")
        sb.append(elements.joinToString(",") { it.asString() })
        sb.append("]")
        return sb.toString()

    }

    fun map(transform: (JsonValue) -> JsonValue) {
        TODO()
    }
}
