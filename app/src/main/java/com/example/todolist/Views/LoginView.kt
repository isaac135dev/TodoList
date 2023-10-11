package com.example.todolist.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.ViewModels.LoginViewModel
import com.example.todolist.ViewModels.MainViewViewModel
import com.example.todolist.databinding.ActivityLoginViewBinding

class LoginView : AppCompatActivity() {

    private lateinit var binding: ActivityLoginViewBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var mainViewViewModel: MainViewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initializeViews()

        mainViewViewModel.currentUserId.observe(this) { userId ->
            if (userId.isNotEmpty()) {
                navigateToToDoList()
            } else {
                setContentView(binding.root)
            }
        }

        initializeFragment(savedInstanceState)

    }

    private fun init() {
        setupListeners()
    }

    private fun initializeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val headerFragment = HeaderFragment.newInstance(
                "To Do List", "Get things done", 15, R.color.pink, -15
            )
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerLogin, headerFragment)
                .commit()
        }
    }

    private fun setupListeners() {
        binding.myTextButton.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun initializeViews() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        mainViewViewModel = ViewModelProvider(this)[MainViewViewModel::class.java]
    }

    private fun setupRegistration() {
        binding.etLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar este método
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.etLoginEmail.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementar este método
            }
        })

        binding.edLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar este método
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.edLoginPassword.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementar este método
            }
        })

        binding.btLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (validateFields(email, password)) {
                viewModel.email = email
                viewModel.password = password
                viewModel.login { isSuccess ->
                    if (isSuccess) {
                        clearErrors()
                        navigateToToDoList()
                    } else {
                        // Mostrar mensaje de error
                        val errorMessage = viewModel.errorMessage
                    }
                }
            } else {
                showErrorMessages()
            }
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        return email.isNotBlank() && email.contains("@") && email.contains(".") &&
                password.isNotBlank() && password.length >= 6
    }

    private fun navigateToToDoList() {
        val intent = Intent(this, ToDoListActivity::class.java)
        startActivity(intent)
    }

    private fun clearErrors() {
        binding.edLoginPassword.error = null
        binding.etLoginEmail.error = null
    }

    private fun showErrorMessages() {
        binding.edLoginPassword.error = getString(R.string.textFieldPasswordError)
        binding.etLoginEmail.error = getString(R.string.textFieldEmailError)
    }

}