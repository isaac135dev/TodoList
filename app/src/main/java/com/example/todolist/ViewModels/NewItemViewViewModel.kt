package com.example.todolist.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.Models.ToDoListItem
import com.example.todolist.Others.toMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Date
import java.util.UUID

class NewItemViewViewModel() : ViewModel() {
    var title = ""
    var dueDate = Calendar.getInstance().time
    private val _showAlert = MutableLiveData<Boolean>()
    val showAlert: LiveData<Boolean>
        get() = _showAlert

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun save() {
        if (canSave()) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            uid?.let {
                val newId = UUID.randomUUID().toString()
                val newItem = ToDoListItem(
                    id = newId,
                    title = title,
                    dueDate = dueDate.time / 1000,
                    createDate = Date().time / 1000,
                    isDone = false
                )

                val db = FirebaseFirestore.getInstance()
                db.collection("users")
                    .document(uid)
                    .collection("todos")
                    .document(newId)
                    .set(newItem.toMap())
                    .addOnSuccessListener {
                        showToast("Item saved successfully")
                    }
                    .addOnFailureListener {
                        showToast("Error saving item")
                    }
            }
        }
    }

    fun canSave(): Boolean {
        if (title.trim().isEmpty()) {
            _showAlert.value = true
            showToast("Please fill in all fields.")
            return false
        }

        if (dueDate < Calendar.getInstance().time) {
            _showAlert.value = true
            showToast("Please select a due date that is today or newer.")
            return false
        }
        return true
    }


    private fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun showErrorDialog() {
        _showAlert.value = true
    }

    fun   hideErrorDialog() {
        _showAlert.value = false
    }
}