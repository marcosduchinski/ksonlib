import model.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

class Person(
    val id: Int,
    val name: String,
    val height: Double,
    val married: Boolean,
    val age: Int? = null
)

fun main() {
    val customer = Person(99, "Maria", 265.2, married = false)
    val customerJson = KsonLib(customer).asJson()
    println()
    println(customerJson)
}




