package com.example.flashcardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        findViewById<ImageView>(R.id.cancelsign).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.saveicon).setOnClickListener {
            val questionString = findViewById<EditText>(R.id.editQuestionField).text.toString()
            val answerString = findViewById<EditText>(R.id.editAnswerField).text.toString()

            val data = Intent() // create a new Intent, this is where we will put our data
            data.putExtra("question_key", questionString) // puts one string into the Intent, with the key as 'question_key'
            data.putExtra("answer_key", answerString) // puts another string into the Intent, with the key as 'answer_key

            setResult(RESULT_OK, data) // set result code and bundle data for response
            finish() // closes this activity and pass data to the original activity that launched this activity
        }
    }
}