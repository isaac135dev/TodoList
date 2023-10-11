package com.example.todolist.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var email: String = ""
    var password: String = ""
    var errorMessage: String = ""

    fun login(callback: (Boolean) -> Unit) {
        if (validate()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    callback(task.isSuccessful)
                }
        } else {
            callback(false)
        }
    }

    private fun validate(): Boolean {
        errorMessage = ""
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            errorMessage = "Please fill in all fields."
            return false
        }
        if (!email.contains("@") || !email.contains(".")) {
            errorMessage = "Please enter a valid email."
            return false
        }
        return true
    }
}
