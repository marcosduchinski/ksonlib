import model.JsonArray
import model.JsonBoolean
import model.JsonNull
import model.JsonNumber
import model.JsonObject
import model.JsonString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


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
        Assertions.assertEquals(validJson, jsonObject.asString())
    }
    @Test
    fun should_validate_json_object_with_basic_types_and_array() {

    }

    @Test
    fun should_validate_json_object_with_basic_types_and_object() {

    }

}