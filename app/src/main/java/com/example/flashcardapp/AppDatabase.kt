package com.example.flashcardapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flashcardapp.Flashcard
import com.example.flashcardapp.FlashcardDao

@Database(entities = [Flashcard::class], version = 1) //tells us its a flashcard database

abstract class AppDatabase : RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao //tells that we will access through the DAO
}
