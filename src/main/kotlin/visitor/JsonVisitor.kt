package visitor

import model.JsonValue

interface JsonVisitor<R> {
    fun visit(value: JsonValue): R
}