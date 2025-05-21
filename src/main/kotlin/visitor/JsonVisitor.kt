package visitor

import model.JsonValue

/**
* An interface for visiting JSON values.
*
* Implements the Visitor pattern to allow different operations to be performed
* on instances of [JsonValue].
*/
interface JsonVisitor {

    /**
     * Visits a [JsonValue].
     *
     * @param value The JSON value to visit.
     */
    fun visit(value: JsonValue)
}