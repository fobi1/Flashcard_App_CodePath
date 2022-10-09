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
    lateinit var flashcardDatabase: FlashcardDatabase //need to create instance of database so we can read and define data
    var allFlashcards = mutableListOf<Flashcard>() //initialized as class variable to point to empty mutable list

    var currCardDisplayedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards= flashcardDatabase.getAllCards().toMutableList()
        //returns a list of flashcard objects and converts it to a mutable list

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)

        if(allFlashcards.size >0) {
            flashcardQuestion.text = allFlashcards[0].question
            flashcardAnswer.text = allFlashcards[0].answer
        }

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

                if(!questionString.isNullOrEmpty() && !answerString.isNullOrEmpty()) {
                    flashcardDatabase.insertCard(Flashcard(questionString, answerString))
                    allFlashcards= flashcardDatabase.getAllCards().toMutableList()
                }
            } else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }
        }

        findViewById<ImageView>(R.id.plussign).setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

        val nextArrow = findViewById<ImageView>(R.id.nextarrow).setOnClickListener {
            if(allFlashcards.isEmpty()){
                //if empty, then we want to early return so the rest of the code doesn't execute
                return@setOnClickListener
            }

            currCardDisplayedIndex++

            if(currCardDisplayedIndex >= allFlashcards.size){
                //go back to the beginning
                currCardDisplayedIndex = 0
            }

            allFlashcards= flashcardDatabase.getAllCards().toMutableList()

            val question = allFlashcards[currCardDisplayedIndex].question
            val answer = allFlashcards[currCardDisplayedIndex].answer

            flashcardQuestion.text = question
            flashcardAnswer.text = answer
        }

    }

}