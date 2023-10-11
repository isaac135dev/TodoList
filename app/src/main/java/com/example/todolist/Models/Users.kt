package com.example.todolist.Models

import java.util.Date

data class User(val id: String, val name: String, val email: String, val joined: Long) {
    fun asMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "joined" to joined
        )
    }
}


