package com.example.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(app: Application, private val repository: NotesRepository) :
    AndroidViewModel(app) {

    fun getAllNotes() = repository.getAllNotes()

    fun insertNote(notes: Notes) {
        viewModelScope.launch {
            repository.insertNote(notes)
        }
    }

    fun updateNote(notes: Notes) {
        viewModelScope.launch {
            repository.updateNote(notes)
        }
    }

    fun deleteNote(notes: Notes) {
        viewModelScope.launch {
            repository.deleteNote(notes)
        }
    }

    fun searchNote(query: String?) = repository.searchNote(query)


}