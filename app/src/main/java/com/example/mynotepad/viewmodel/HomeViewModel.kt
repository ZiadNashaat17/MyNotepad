package com.example.mynotepad.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mynotepad.room.Note
import com.example.mynotepad.room.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dao: NoteDao) : ViewModel() {
    var notes by mutableStateOf(emptyList<Note>())

    fun insertNote(note: Note) {
        dao.insert(note)
    }

    fun getAllNotes() {
        notes = dao.retrieveAll()
    }

    fun getSelectedNote(id: Int): Note {
        return dao.retrieveSelected(id)
    }

    fun deleteSelectedNote(note: Note) {
        dao.delete(note)
    }

    fun updateSelectedNote(note: Note) {
        dao.update(note)
    }

    fun getStartingWith(firstChars: String) {
        notes = dao.retrieveStartingWith(firstChars)
    }

    fun sortDesc() {
        notes = dao.retrieveDesc()
    }

    fun sortAlphabeticallyAscending() {
        notes = dao.retrieveAlphabeticalAscending()
    }

    fun sortAlphabeticallyDescending() {
        notes = dao.retrieveAlphabeticalDescending()
    }
}