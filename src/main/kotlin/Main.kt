data class Course(val name: String, val credits: Int, val evaluation: List<EvalItem>)
data class EvalItem(val name: String, val percentage: Double, val mandatory: Boolean, val type: EvalType?)
enum class EvalType { TEST, PROJECT, EXAM }

fun main() {
    val course =
        Course("PA", 6, listOf(EvalItem("quizzes", .2, false, null), EvalItem("project", .8, true, EvalType.PROJECT)))
    val customerJson = KsonLib(course).asJson()
    println()
    println(customerJson)
}



