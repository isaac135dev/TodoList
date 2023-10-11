package com.example.todolist.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.ViewModels.NewItemViewViewModel
import com.example.todolist.ViewModels.RegisterViewModel
import com.example.todolist.databinding.ActivityNewItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

class NewItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewItemBinding
    private lateinit var viewModel: NewItemViewViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        setupUi()
    }

    private fun initializeViews() {
        viewModel = ViewModelProvider(this)[NewItemViewViewModel::class.java]

    }

    private fun setupUi() {
        binding.saveButton.setOnClickListener {
            viewModel.title = binding.titleEditText.text.toString()
            setDatePiker()

            if (viewModel.canSave()) {
                viewModel.save()
                finish()
            }else {
                viewModel.showErrorDialog()
            }
        }

        viewModel.showAlert.observe(this) { showAlert ->
            if (showAlert) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please fill in all fields and select a valid due date.")
                    .setPositiveButton("OK", null)
                    .create()

                alertDialog.show()

                // Una vez que se muestra el diálogo, restablece el valor de showAlert
                viewModel.hideErrorDialog()
            }
        }

    }

    private fun setDatePiker(){
        val selectedYear = binding.dueDatePicker.year
        val selectedMonth = binding.dueDatePicker.month
        val selectedDay = binding.dueDatePicker.dayOfMonth

        val calendar = Calendar.getInstance()

        calendar.set(selectedYear, selectedMonth, selectedDay)

        val selectedDate = calendar.time

        viewModel.dueDate = selectedDate
    }
}