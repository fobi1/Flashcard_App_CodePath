package com.example.flashcardapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.flashcard_question).setOnClickListener {
            findViewById<TextView>(R.id.flashcard_question).visibility = View.INVISIBLE
            findViewById<TextView>(R.id.flashcard_answer).visibility = View.VISIBLE
        }
        findViewById<TextView>(R.id.flashcard_answer).setOnClickListener {
            findViewById<TextView>(R.id.flashcard_question).visibility = View.VISIBLE
            findViewById<TextView>(R.id.flashcard_answer).visibility = View.INVISIBLE
        }
        findViewById<TextView>(R.id.answerChoice1).setOnClickListener {
            findViewById<View>(R.id.answerChoice1).setBackgroundColor(getResources().getColor(R.color.red_100, null))
        }
        findViewById<TextView>(R.id.answerChoice2).setOnClickListener {
            findViewById<View>(R.id.answerChoice2).setBackgroundColor(getResources().getColor(R.color.red_100, null))
        }
        findViewById<TextView>(R.id.answerChoice3).setOnClickListener {
            findViewById<View>(R.id.answerChoice3).setBackgroundColor(getResources().getColor(R.color.green_100, null))
        }

        findViewById<ImageView>(R.id.thirdEyeClosed).setOnClickListener {
            findViewById<TextView>(R.id.answerChoice1).visibility = View.INVISIBLE
            findViewById<TextView>(R.id.answerChoice2).visibility = View.INVISIBLE
            findViewById<TextView>(R.id.answerChoice3).visibility = View.INVISIBLE
            findViewById<ImageView>(R.id.thirdEyeOpen).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.thirdEyeClosed).visibility = View.INVISIBLE
        }
        findViewById<ImageView>(R.id.thirdEyeOpen).setOnClickListener {
            findViewById<TextView>(R.id.answerChoice1).visibility = View.VISIBLE
            findViewById<TextView>(R.id.answerChoice2).visibility = View.VISIBLE
            findViewById<TextView>(R.id.answerChoice3).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.thirdEyeClosed).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.thirdEyeOpen).visibility = View.INVISIBLE

        }

    }
}