import exception.JsonValueClassCastException
import exception.JsonValueUnsupportedTypeException
import exception.JsonValueValidationException
import model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Test suite for the [KsonLib] JSON serialization library.
 *
 * This class verifies correct serialization of basic Kotlin types,
 * collections, enums, and custom data classes.
 *
 * It also tests that validation and error handling (e.g., unsupported types,
 * type mismatches in arrays, and class cast exceptions) are properly triggered.
 */
class KsonLibTest {

    data class Course(val name: String, val credits: Int, val evaluation: List<EvalItem>)
    data class EvalItem(val name: String, val percentage: Double, val mandatory: Boolean, val type: EvalType?)
    enum class EvalType { TEST, PROJECT, EXAM }


    @Test
    fun should_serialize_int() {
        val value: Int = 10
        Assertions.assertEquals(value, (KsonLib(value).asJson()).toInt())
    }

    @Test
    fun should_serialize_double() {
        val value: Double = 10.1
        Assertions.assertEquals(value, (KsonLib(value).asJson()).toDouble())
    }

    @Test
    fun should_serialize_boolean() {
        val value: Boolean = true
        Assertions.assertEquals(value, (KsonLib(value).asJson()).toBoolean())
    }

    @Test
    fun should_serialize_null() {
        val value = null
        Assertions.assertEquals("null", (KsonLib(value).asJson()))
    }

    @Test
    fun should_serialize_string() {
        val value: String = "test"
        val expected = "\"test\""
        Assertions.assertEquals(expected, (KsonLib(value).asJson()))
    }

    @Test
    fun should_serialize_enum() {
        val value: EvalType = EvalType.TEST
        val expected = "\"${value.toString()}\""
        Assertions.assertEquals(expected, (KsonLib(value).asJson()))
    }

    @Test
    fun should_serialize_empty_list() {
        val emptyList = emptyList<String>()
        val expected = JsonArray()
        Assertions.assertEquals(expected, (KsonLib(emptyList).asJsonArray()))
    }

    @Test
    fun should_serialize_empty_map() {
        val emptyMap = emptyMap<String, String>()
        val expected = JsonObject()
        Assertions.assertEquals(expected, (KsonLib(emptyMap).asJsonObject()))
    }

    @Test
    fun should_throw_jsonvalue_validation_exception_to_list_with_different_tyes() {
        val emptyList = mutableListOf("two", 2)
        assertThrows<JsonValueValidationException> {
            KsonLib(emptyList).asJson()
        }
    }

    @Test
    fun should_throw_jsonvalue_validation_exception_to_map_containing_list_with_different_tyes() {
        val listWithDifferentTypes = mutableListOf("two", 2)
        val map = mapOf<String, List<Any>>("different" to listWithDifferentTypes)
        assertThrows<JsonValueValidationException> {
            KsonLib(map).asJson()
        }
    }

    @Test
    fun should_throw_unsupported_type_exception() {
        val unsupportedType = arrayOf<Number>(1, 2, 3)
        assertThrows<JsonValueUnsupportedTypeException> {
            KsonLib(unsupportedType).asJson()
        }
        val unsupportedListType = listOf<Array<Number>>(
            arrayOf(1, 2, 3),
            arrayOf<Number>(1, 2, 3)
        )
        assertThrows<JsonValueUnsupportedTypeException> {
            KsonLib(unsupportedListType).asJson()
        }
        val unsupportedMapType = mapOf<String, Array<Number>>(
            "1" to arrayOf(1, 2, 3)
        )
        assertThrows<JsonValueUnsupportedTypeException> {
            KsonLib(unsupportedMapType).asJson()
        }
        val mapOfJsonValue = mapOf<String, JsonString>(
            "key" to JsonString("value")
        )
        assertThrows<JsonValueUnsupportedTypeException> {
            KsonLib(mapOfJsonValue).asJsonArray()
        }

        val mapOfArray = mapOf<String, Array<Number>>(
            "key" to arrayOf(1, 2, 3)
        )
        assertThrows<JsonValueUnsupportedTypeException> {
            KsonLib(mapOfArray).asJsonArray()
        }
    }

    @Test
    fun should_throw_jsonvalue_cast_class_exception_json_object() {
        val mapOfJsonValue = mapOf<String, String>(
            "key" to "value",
        )
        assertThrows<JsonValueClassCastException> {
            KsonLib(mapOfJsonValue).asJsonArray()
        }
    }

    @Test
    fun should_throw_jsonvalue_cast_class_exception_json_array() {
        val mapOfJsonValue = listOf<Int>(1, 2, 3)
        assertThrows<JsonValueClassCastException> {
            KsonLib(mapOfJsonValue).asJsonObject()
        }
    }

    @Test
    fun should_serialize_course() {

        val course = Course(
            "PA",
            6,
            listOf(
                EvalItem("quizzes", .2, false, null),
                EvalItem("project", .8, true, EvalType.PROJECT)
            )
        )

        val expectedJson = JsonObject(
            "name" to JsonString("PA"),
            "credits" to JsonNumber(6),
            "evaluation" to JsonArray(
                JsonObject(
                    "name" to JsonString("quizzes"),
                    "percentage" to JsonNumber(.2),
                    "mandatory" to JsonBoolean(false),
                    "type" to JsonNull
                ),
                JsonObject(
                    "name" to JsonString("project"),
                    "percentage" to JsonNumber(.8),
                    "mandatory" to JsonBoolean(true),
                    "type" to JsonString("PROJECT")
                )
            )
        )
        Assertions.assertEquals(expectedJson, KsonLib(course).asJsonObject())
    }

    fun getCourses(): List<Course> {
        val pa = Course(
            "PA",
            6,
            listOf(
                EvalItem("quizzes", .2, false, null),
                EvalItem("project", .8, true, EvalType.PROJECT)
            )
        )

        val robotica = Course(
            "ROBOTICA",
            6,
            listOf(
                EvalItem("test", .5, true, EvalType.TEST),
                EvalItem("project", .5, true, EvalType.PROJECT)
            )
        )

        return listOf<Course>(pa, robotica)
    }

    @Test
    fun should_filter_course() {

        val courses = getCourses()

        val expectedJson = JsonArray(
            JsonObject(
                "name" to JsonString("PA"),
                "credits" to JsonNumber(6),
                "evaluation" to JsonArray(
                    JsonObject(
                        "name" to JsonString("quizzes"),
                        "percentage" to JsonNumber(.2),
                        "mandatory" to JsonBoolean(false),
                        "type" to JsonNull
                    ),
                    JsonObject(
                        "name" to JsonString("project"),
                        "percentage" to JsonNumber(.8),
                        "mandatory" to JsonBoolean(true),
                        "type" to JsonString("PROJECT")
                    )
                )
            )
        )

        val jsonArray = KsonLib(courses).asJsonArray()
        assertNotNull(jsonArray)

        val jsonPA = jsonArray.filter {
            it is JsonObject && it.get("name")!!.asJson() == "\"PA\""
        }

        assertEquals(expectedJson, jsonPA)
    }

    @Test
    fun should_map_course() {

        val courses = getCourses()

        val expectedJson = JsonArray(
            JsonObject(
                "name" to JsonString("PA"),
                "credits" to JsonNumber(12.0),
                "evaluation" to JsonArray(
                    JsonObject(
                        "name" to JsonString("quizzes"),
                        "percentage" to JsonNumber(.2),
                        "mandatory" to JsonBoolean(false),
                        "type" to JsonNull
                    ),
                    JsonObject(
                        "name" to JsonString("project"),
                        "percentage" to JsonNumber(.8),
                        "mandatory" to JsonBoolean(true),
                        "type" to JsonString("PROJECT")
                    )
                )
            ),
            JsonObject(
                "name" to JsonString("ROBOTICA"),
                "credits" to JsonNumber(12.0),
                "evaluation" to JsonArray(
                    JsonObject(
                        "name" to JsonString("test"),
                        "percentage" to JsonNumber(.5),
                        "mandatory" to JsonBoolean(true),
                        "type" to JsonString("TEST")
                    ),
                    JsonObject(
                        "name" to JsonString("project"),
                        "percentage" to JsonNumber(.5),
                        "mandatory" to JsonBoolean(true),
                        "type" to JsonString("PROJECT")
                    )
                )
            )
        )

        val jsonArray = KsonLib(courses).asJsonArray()
        assertNotNull(jsonArray)

        val jsonPA = jsonArray.map { jsonValue ->
            if (jsonValue is JsonObject) {
                val updatedMembers = jsonValue.members.mapValues { (_, value) ->
                    if (value is JsonNumber) {
                        JsonNumber(value.asDouble() * 2)
                    } else {
                        value
                    }
                }
                JsonObject(*updatedMembers.toList().toTypedArray())
            } else {
                jsonValue
            }
        }

        assertEquals(expectedJson, jsonPA)
    }
}

