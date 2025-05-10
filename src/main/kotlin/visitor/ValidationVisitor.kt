package visitor

import exception.JsonValueValidationException
import model.JsonArray
import model.JsonObject
import model.JsonValue

class ValidationVisitor : JsonVisitor {
    override fun visit(value: JsonValue) {
        when (value) {
            is JsonObject -> validateJsonObject(value)
            is JsonArray -> validateJsonArray(value)
            else -> ""
        }
    }

    private fun validateJsonObject(value: JsonObject) {
        if (value.members.isEmpty())
            throw JsonValueValidationException("JsonObject must have at least one member")
        value.members.forEach {
            visit(it.value)
        }
    }

    private fun validateJsonArray(value: JsonArray) {
        if (value.elements.isEmpty()) {
            throw JsonValueValidationException("Array must not be empty")
        }
        val firstType = value.elements.first()::class
        if (!value.elements.all { it::class == firstType }) {
            throw JsonValueValidationException("All types in array must be the same")
        }
    }

}