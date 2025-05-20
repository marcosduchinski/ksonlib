package visitor

import exception.JsonValueValidationException
import model.JsonArray
import model.JsonValue

class ValidationVisitor : JsonVisitor {
    override fun visit(value: JsonValue) {
        if (value is JsonArray) {
            validateJsonArray(value)
        }
    }
    private fun validateJsonArray(value: JsonArray) {
        if (value.elements.isNotEmpty()) {
            val firstType = value.elements.first()::class
            if (!value.elements.all { it::class == firstType }) {
                throw JsonValueValidationException("All types in array must be the same")
            }
        }
    }
}