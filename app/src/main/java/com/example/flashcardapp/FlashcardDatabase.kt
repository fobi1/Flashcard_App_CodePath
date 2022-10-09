package com.example.flashcardapp

import android.content.Context
import androidx.room.Room

class FlashcardDatabase internal constructor(context: Context) {

    private val db: AppDatabase

    fun initFirstCard() { //inserts a flashcard into databse since it starts off empty
        if (db.flashcardDao().getAll().isEmpty()) {
            insertCard(Flashcard("Who is the 44th President of the United States", "Barack Obama"))
        }
    }

    fun getAllCards(): List<Flashcard> {
        return db.flashcardDao().getAll()
    }

    fun insertCard(flashcard: Flashcard) { //uses DAO method to insert flashcard into database
        db.flashcardDao().insertAll(flashcard)
    }

    fun deleteCard(flashcardQuestion: String) {
        val allCards = db.flashcardDao().getAll()
        for (card in allCards) {
            if (card.question == flashcardQuestion) {
                db.flashcardDao().delete(card)
            }
        }
    }

    fun updateCard(flashcard: Flashcard) {
        db.flashcardDao().update(flashcard)
    }

    fun deleteAll() {
        for (flashcard in db.flashcardDao().getAll()) {
            db.flashcardDao().delete(flashcard)
        }
    }

    init { //allows us to interact with database and call these methods
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "flashcard-database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}