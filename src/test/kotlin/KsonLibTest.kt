import model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
    fun should_throw_illegal_argument_exception_to_empty_list() {
        val emptyList = emptyList<String>()
        assertThrows<IllegalArgumentException> {
            KsonLib(emptyList).asJson()
        }
    }

    @Test
    fun should_throw_illegal_argument_exception_to_empty_map() {
        val emptyMap = emptyMap<String, String>()
        assertThrows<IllegalArgumentException> {
            KsonLib(emptyMap).asJson()
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
        ).asJson()

        Assertions.assertEquals(expectedJson, KsonLib(course).asJson())
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
        ).asJson()

        val jsonArray = KsonLib(courses).asJsonArray()
        assertNotNull(jsonArray)

        val jsonPA = jsonArray.filter {
            it is JsonObject && it.get("name")!!.asJson() == "\"PA\""
        }.asJson()

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
        ).asJson()

        val jsonArray = KsonLib(courses).asJsonArray()
        assertNotNull(jsonArray)

        val jsonPA = jsonArray.map { jsonValue ->
            if (jsonValue is JsonObject) {
                val updatedMembers = jsonValue.members.mapValues { (_, value) ->
                    if (value is JsonNumber) {
                        JsonNumber(value.asFloat() * 2)
                    } else {
                        value
                    }
                }
                JsonObject(*updatedMembers.toList().toTypedArray())
            } else {
                jsonValue
            }
        }.asJson()

        assertEquals(expectedJson, jsonPA)
    }
}

