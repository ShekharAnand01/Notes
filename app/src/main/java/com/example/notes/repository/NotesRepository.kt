package com.example.notes.repository

import com.example.notes.database.NotesDatabase
import com.example.notes.model.Notes

class NotesRepository(private val db: NotesDatabase) {

    suspend fun insertNote(notes: Notes) = db.getNotesDao().insertNote(notes)
    suspend fun updateNote(notes: Notes) = db.getNotesDao().updateNote(notes)
    suspend fun deleteNote(notes: Notes) = db.getNotesDao().deleteNote(notes)

    fun getAllNotes() = db.getNotesDao().getAllNotes()
    fun searchNote(query: String?) = db.getNotesDao().searchNote(query)

}