package com.example.notes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.repository.NotesRepository
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModel()
    }

    private fun setupViewModel() {
        val repository = NotesRepository(NotesDatabase(this))
        val viewModelFactory = NotesViewModelFactory(application, repository)
        viewModel=  ViewModelProvider(this,viewModelFactory)[NotesViewModel::class.java]
    }
}