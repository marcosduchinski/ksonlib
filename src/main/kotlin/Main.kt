import model.JsonObject
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMembers

/*
https://www.json.org/json-en.html
https://datatracker.ietf.org/doc/html/rfc8259
Goal: Develop classes to represent JSON values (model),
allowing manipulation operations and serialization to strings (standard format).

value
├── object
│   └── { members }
│       └── pair (name: value)
│           ├── string (as name)
│           └── value (recursive)
├── array
│   └── [ elements ]
│       └── value (recursive)
├── string
├── number
├── true
├── false
└── null
 */

fun serialize(clazz: KClass<*>): String {
    clazz.declaredMembers.forEach { member ->
        println(member)
    }
    return JsonObject(members = mutableMapOf()).asString();
}




