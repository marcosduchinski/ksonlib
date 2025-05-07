import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
        assertThrows<IllegalArgumentException>{
            KsonLib(emptyList).asJson()
        }
    }

    @Test
    fun should_throw_illegal_argument_exception_to_empty_map() {
        val emptyMap = emptyMap<String, String>()
        assertThrows<IllegalArgumentException>{
            KsonLib(emptyMap).asJson()
        }
    }

    @Test
    fun should_serialize_course () {
        val course = Course("PA", 6, listOf(EvalItem("quizzes", .2, false, null), EvalItem("project", .8, true, EvalType.PROJECT)))
        val expected = "{\"name\":\"PA\",\"credits\":6,\"evaluation\":[{\"name\":\"quizzes\",\"percentage\":0.2,\"mandatory\":false,\"type\":null},{\"name\":\"project\",\"percentage\":0.8,\"mandatory\":true,\"type\":\"PROJECT\"}]}"
        Assertions.assertEquals(expected.replace("\\s+",""), KsonLib(course).asJson().replace("\\s+",""))
    }


}