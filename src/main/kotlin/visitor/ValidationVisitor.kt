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

    private fun validateJsonString(value: JsonString){

        if(value.asJson().length < 2 || value.asJson().first() != '"' || value.asJson().last() != '"'){
            println("Não sou uma string")
        }
    }

    private fun validateJsonObject(value: JsonObject) {
        val valid = true
        if (!valid) {
            throw IllegalArgumentException("Ilnvalid json object")
        }
    }

    private fun validateJsonArray(value: JsonArray) {

    }

}