package visitor

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
            throw IllegalArgumentException("JsonObject must have at least one member")
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