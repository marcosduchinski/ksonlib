import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class TestCase {

    @Test
    fun should_do_something() {
        val json = JsonObject(
            mutableMapOf(
                JsonString("name") to JsonString("some name"),
                JsonString("credit") to JsonNumber(10),
                JsonString("evaluation") to JsonArray(
                    elements = mutableListOf(
                        JsonNumber(10),
                        JsonNumber(10),
                        JsonNumber(10)
                    )
                )
            )
        )
    }
}