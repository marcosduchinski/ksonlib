package visitor

import model.JsonValue

interface JsonVisitor {
    fun visit(value: JsonValue)
}