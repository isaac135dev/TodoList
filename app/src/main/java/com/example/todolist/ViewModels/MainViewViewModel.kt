package com.example.todolist.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _currentUserId = MutableLiveData<String>()
    val currentUserId: LiveData<String>
        get() = _currentUserId

    init {
        auth.addAuthStateListener {firebaseAuth ->
            val user = firebaseAuth.currentUser
            _currentUserId.value = user?.uid ?: ""
        }
    }

    val isSignedIn: Boolean
        get() = auth.currentUser != null

//    private var authStateListener: FirebaseAuth.AuthStateListener =
//        FirebaseAuth.AuthStateListener { auth ->
//            val user = auth.currentUser
//            currentUserId = user?.uid ?: ""
//        }

//    init {
//        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)
//    }

//    val isSignedIn: Boolean
//        get() = FirebaseAuth.getInstance().currentUser != null
//
//    override fun onCleared() {
//        super.onCleared()
//        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener)
//    }
}