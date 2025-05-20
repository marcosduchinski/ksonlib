package visitor

import model.JsonValue

/**
 * JsonVisitor is an interface for visiting JSON values.
 * It allows for different operations to be performed on JSON values.
 */
interface JsonVisitor {
    /**
     * Visits a JSON value.
     *
     * @param value The JSON value to visit.
     */
    fun visit(value: JsonValue)
}