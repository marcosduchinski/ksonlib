package visitor

import model.JsonArray
import model.JsonObject
import model.JsonString
import model.JsonValue

class ValidationVisitor : JsonVisitor<JsonValue> {
    override fun visit(value: JsonValue): JsonValue {
        when (value) {
            is JsonString -> validateJsonString(value)
            is JsonObject -> validateJsonObject(value)
            is JsonArray -> validateJsonArray(value)
            else -> ""
        }
        return value
    }

    private fun validateJsonString(value: JsonString) {
        if (value.asJson().length < 2 || value.asJson().first() != '"' || value.asJson().last() != '"') {
            throw IllegalArgumentException("Invalid string")
        }
    }

    private fun validateJsonObject(value: JsonObject) {
        if (value.members.isEmpty())
            throw IllegalArgumentException("Must have at least one member")
        value.members.forEach {
            visit(it.value)
        }
    }

    private fun validateJsonArray(value: JsonArray) {
        if (value.elements.isEmpty()) {
            throw IllegalArgumentException("Array must not be empty")
        }
        val firstType = value.elements.first()::class
        if (!value.elements.all { it::class == firstType }) {
            throw IllegalArgumentException("All types in array must be the same")
        }
    }

}