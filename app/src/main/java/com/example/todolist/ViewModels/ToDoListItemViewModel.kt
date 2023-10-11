package com.example.todolist.ViewModels

import androidx.lifecycle.ViewModel
import com.example.todolist.Models.ToDoListItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ToDoListItemViewModel: ViewModel() {

    fun toggleIsDone(item: ToDoListItem) {
        val itemCopy = item.copy(isDone = !item.isDone)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users")
                .document(userId)
                .collection("todos")
                .document(itemCopy.id)
                .set(itemCopy)
        }

    }

}