package com.example.todolist.Others

import com.google.gson.Gson

inline fun <reified T : Any> T.toMap(): Map<String, Any> {
    val json = Gson().toJson(this)
    val map = Gson().fromJson(json, Map::class.java)
    @Suppress("UNCHECKED_CAST")
    return map as? Map<String, Any> ?: emptyMap()
}
