package com.example.mynotepad

import com.example.mynotepad.room.Note
import com.example.mynotepad.room.NoteDao
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {

    fun insertNote(note: Note) = dao.insert(note)

    fun getAllNotes() = dao.retrieveAll()


    fun getSelectedNote(id: Int) = dao.retrieveSelected(id)


    fun deleteSelectedNote(note: Note) = dao.delete(note)

    fun updateSelectedNote(note: Note) = dao.update(note)


    fun getStartingWith(firstChars: String) = dao.retrieveStartingWith(firstChars)


    fun sortDesc() = dao.retrieveDesc()


    fun sortAlphabeticallyAscending() = dao.retrieveAlphabeticalAscending()


    fun sortAlphabeticallyDescending() = dao.retrieveAlphabeticalDescending()


}