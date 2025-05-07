import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KsonLibTest {
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


}