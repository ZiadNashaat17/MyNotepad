package com.example.mynotepad.viewmodel


import androidx.lifecycle.ViewModel
import com.example.mynotepad.NoteRepository
import com.example.mynotepad.room.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    var notes: List<Note> = mutableListOf()

    fun insertNote(note: Note) {
        noteRepository.insertNote(note)
    }

    fun getAllNotes() {
        notes = noteRepository.getAllNotes()
    }

    fun getSelectedNote(id: Int): Note {
        return noteRepository.getSelectedNote(id)
    }

    fun deleteSelectedNote(note: Note) {
        noteRepository.deleteSelectedNote(note)
    }

    fun updateSelectedNote(note: Note) {
        noteRepository.updateSelectedNote(note)
    }

    fun getStartingWith(firstChars: String) {
        notes = noteRepository.getStartingWith(firstChars)
    }

    fun sortDesc() {
        notes = noteRepository.sortDesc()
    }

    fun sortAlphabeticallyAscending() {
        notes = noteRepository.sortAlphabeticallyAscending()
    }

    fun sortAlphabeticallyDescending() {
        notes = noteRepository.sortAlphabeticallyDescending()
    }
}