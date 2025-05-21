package visitor

import exception.JsonValueValidationException
import model.JsonArray
import model.JsonValue

/**
 * A visitor that validates JSON values.
 *
 * Specifically, it checks whether all elements in a [JsonArray] are of the same type.
 */
class ValidationVisitor : JsonVisitor {

    /**
     * Visits a [JsonValue] and performs validation if it's a [JsonArray].
     *
     * @param value The JSON value to visit and validate.
     */
    override fun visit(value: JsonValue) {
        if (value is JsonArray) {
            validateJsonArray(value)
        }
    }

    /**
     * Validates that all elements in the given [JsonArray] are of the same type.
     *
     * @param value The [JsonArray] to validate.
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