package com.example.mynotepad.di

import android.content.Context
import androidx.room.Room
import com.example.mynotepad.room.NoteDao
import com.example.mynotepad.room.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "Note_Database")
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideAppModule(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()

}