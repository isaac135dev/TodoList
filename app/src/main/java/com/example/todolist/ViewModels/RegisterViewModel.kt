package com.example.todolist.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.todolist.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    var name = ""
    var email = ""
    var password = ""

    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    fun register(callback: (Boolean) -> Unit) {

        if (!validate()) {
            callback(false)
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                insertUserRecord(auth.currentUser?.uid ?: "")
                callback(true)
            } else {
                callback(false)
            }
        }

    }

    private fun insertUserRecord(id: String) {
        val newUser = User(
            id,
            name,
            email,
            System.currentTimeMillis() / 1000
        )
        fireStore.collection("users")
            .document(id)
            .set(newUser.asMap())
    }

    private fun validate(): Boolean {
        if (name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()){
            return false
        }

        if (!email.contains("@") || !email.contains(".")){
            return false
        }

        if (password.length < 6) {
            return false
        }
        return true
    }
}