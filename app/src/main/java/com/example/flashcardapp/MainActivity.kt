package com.example.flashcardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

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

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            if (data != null) { // Check that we have data returned - if data DOES NOT EQUAL null
                val questionString = data.getStringExtra("question_key") // 'string1' needs to match the key we used when we put the string in the Intent
                val answerString = data.getStringExtra("answer_key")

                findViewById<TextView>(R.id.flashcard_question).text = questionString
                findViewById<TextView>(R.id.flashcard_answer).text = answerString


                // Log the value of the strings for easier debugging
                Log.i("MainActivity", "question: $questionString")
                Log.i("MainActivity", "answer: $answerString")
            } else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }
        }

        findViewById<ImageView>(R.id.plussign).setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

    }
}