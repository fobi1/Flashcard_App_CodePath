package com.example.flashcardapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcard")
    fun getAll(): List<Flashcard> //can use this function to get all the cards in the database

    @Insert
    fun insertAll(vararg flashcards: Flashcard) //insert flashcards in database

    @Delete
    fun delete(flashcard: Flashcard) //delete flashcards in database

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(flashcard: Flashcard) //updates flashcards in database
}
