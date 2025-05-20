package visitor

import exception.JsonValueValidationException
import model.JsonArray
import model.JsonValue

/**
 * ValidationVisitor is a visitor that validates JSON values.
 * It checks if all elements in a JsonArray are of the same type.
 */
class ValidationVisitor : JsonVisitor {
    override fun visit(value: JsonValue) {
        if (value is JsonArray) {
            validateJsonArray(value)
        }
    }
    /**
     * Validates a JsonArray by checking if all elements are of the same type.
     *
     * @param value The JsonArray to validate.
     * @throws JsonValueValidationException if the array contains elements of different types.
     */
    private fun validateJsonArray(value: JsonArray) {
        if (value.elements.isNotEmpty()) {
            val firstType = value.elements.first()::class
            if (!value.elements.all { it::class == firstType }) {
                throw JsonValueValidationException("All types in array must be the same")
            }
        }
    }
}