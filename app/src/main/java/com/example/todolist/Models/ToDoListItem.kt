package com.example.todolist.Models

data class ToDoListItem (
    val id: String = "",
    val title: String = "",
    val dueDate: Long = 0,
    val createDate: Long = 0,
    var isDone: Boolean = false
)




