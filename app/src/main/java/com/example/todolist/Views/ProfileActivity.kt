package com.example.todolist.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.R
import com.example.todolist.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}