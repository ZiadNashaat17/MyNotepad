package com.example.mynotepad.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Query("SELECT * FROM NOTES_TABLE")
    fun retrieveAll(): List<Note>

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM NOTES_TABLE WHERE id =:id")
    fun retrieveSelected(id: Int): Note

    @Query("SELECT * FROM NOTES_TABLE WHERE title LIKE :firstChars || '%'")
    fun retrieveStartingWith(firstChars: String): List<Note>

    @Query("SELECT * FROM NOTES_TABLE ORDER BY ID DESC")
    fun retrieveDesc(): List<Note>

    @Query("SELECT * FROM NOTES_TABLE ORDER BY title")
    fun retrieveAlphabeticalAscending(): List<Note>

    @Query("SELECT * FROM NOTES_TABLE ORDER BY title DESC")
    fun retrieveAlphabeticalDescending(): List<Note>

    @Query("SELECT * FROM NOTES_TABLE ORDER BY ID LIMIT 1")
    fun retrieveFirst(): List<Note>

    @Query("SELECT * FROM NOTES_TABLE ORDER BY ID DESC LIMIT 1")
    fun retrieveLast(): List<Note>



//    @Query("SELECT * FROM NOTES_TABLE ORDER BY date")
//    fun retrieveWithDateAscending()
//
//    @Query("SELECT * FROM NOTES_TABLE ORDER BY date DESC")
//    fun retrieveWithDateDescending()
}