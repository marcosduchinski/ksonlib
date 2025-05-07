import model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import visitor.ValidationVisitor


class TestCase {

    @Test
    fun should_validate_json_object_with_basic_types() {
        val validJson = "{\"id\": 99,\"name\": \"Maria\",\"height\": 165.3,\"married\": true,\"age\": null}"
        val jsonObject = JsonObject(
            mutableMapOf(
                "id" to JsonNumber(99),
                "name" to JsonString("Maria"),
                "height" to JsonNumber(165.3),
                "married" to JsonBoolean(true),
                "age" to JsonNull()
            )
        )
        val jsonArray = JsonArray(
            mutableListOf(
                JsonString("hello"),
                JsonNumber(45),
                JsonNumber(2)
            )
            )
        val filteredArray = jsonArray.filter { it is JsonNumber && (it as JsonNumber).value.toInt() > 2 }
        //print(filteredArray)
        val visitor = ValidationVisitor()
        visitor.visit(jsonArray)



        Assertions.assertEquals(validJson, jsonObject.asJson())

    }
    @Test
    fun should_validate_json_object_with_basic_types_and_array() {

    }

    @Test
    fun should_validate_json_object_with_basic_types_and_object() {

    }

}