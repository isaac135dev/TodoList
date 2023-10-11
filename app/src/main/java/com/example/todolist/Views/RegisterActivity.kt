package com.example.todolist.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.ViewModels.RegisterViewModel
import com.example.todolist.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        initializeFragment(savedInstanceState)

        setupRegistration()
    }

    private fun initializeViews() {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    private fun initializeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val headerFragment = HeaderFragment.newInstance(
                "Register", "Start organizing tods", -15, R.color.headerRegister, 15
            )
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, headerFragment)
                .commit()
        }
    }

    private fun setupRegistration() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar este método
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tIEmail.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementar este método
            }
        })

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar este método
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tIName.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementar este método
            }
        })

        binding.edPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar este método
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tiPassword.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementar este método
            }
        })

        binding.btRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.edPassword.text.toString()

            if (validateFields(email, password)) {
                viewModel.name = binding.etName.text.toString()
                viewModel.email = email
                viewModel.password = password

                viewModel.register { isSuccess ->
                    if (isSuccess) {
                        clearErrors()
                        navigateToToDoList()
                    } else {
                        // Mostrar mensaje de error
                    }
                }
            } else {
                showErrorMessages()
            }
        }
    }

    private fun clearErrors() {
        binding.tiPassword.error = null
        binding.tIEmail.error = null
        binding.tIName.error = null
    }

    private fun showErrorMessages() {
        binding.tiPassword.error = getString(R.string.textFieldPasswordError)
        binding.tIEmail.error = getString(R.string.textFieldEmailError)
        binding.tIName.error = getString(R.string.textFieldNameError)
    }

    private fun navigateToToDoList() {
        val intent = Intent(this, ToDoListActivity::class.java)
        startActivity(intent)
    }

    private fun validateFields(email: String, password: String): Boolean {
        return email.isNotBlank() && email.contains("@") && email.contains(".") &&
                password.isNotBlank() && password.length >= 6
    }


}
