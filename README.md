


# Tutorial: Getting Started with KsonLib

This tutorial will guide you through the process of using **KsonLib**, a lightweight Kotlin-based library for converting Kotlin data structures into custom JSON representations.

## Goal

Serialize a Kotlin data class into a JSON string.

## Step 1: Define Your Data Class

```kotlin
data class Course(val name: String, val credits: Int)
```

## Step 2: Create an Instance

```kotlin
val course = Course("Kotlin Basics", 5)
```

## Step 3: Convert to JSON

```kotlin
val json = KsonLib(course).asJson()
println(json)  // Output: {"name":"Kotlin Basics","credits":5}
```

## Step 4: Work with JsonObject

```kotlin
val jsonObject = KsonLib(course).asJsonObject()
val nameValue = jsonObject.get("name")
println(nameValue?.asJson())  // Output: "Kotlin Basics"
```


# Example of how to use the library:

https://github.com/marcosduchinski/getjson

---

That's it! You've just converted a Kotlin object into JSON using KsonLib.
