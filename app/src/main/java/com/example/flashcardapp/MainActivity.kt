package com.example.flashcardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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

        flashcardQuestion.setOnClickListener {
            // get the center for the clipping circle
            val cx = flashcardAnswer.width / 2
            val cy = flashcardQuestion.height / 2

            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()


            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(flashcardAnswer, cx, cy, 0f, finalRadius)


            // hide the question and show the answer to prepare for playing the animation!
            flashcardQuestion.visibility = View.INVISIBLE
            flashcardAnswer.visibility = View.VISIBLE

            anim.duration = 1000
            anim.start()
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
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        val nextArrow = findViewById<ImageView>(R.id.nextarrow).setOnClickListener {
            if(allFlashcards.isEmpty()){
                //if empty, then we want to early return so the rest of the code doesn't execute
                return@setOnClickListener
            }



            //OVERALL: adding animation for when next card is shown
            //(1) load resource animation files
            val leftOutAnim = AnimationUtils.loadAnimation(it.context, R.anim.left_out)
            val rightInAnim = AnimationUtils.loadAnimation(it.context, R.anim.right_in)

            //(2) play the two animations in sequence (left then right) by setting listeners-
            //to know when the animation finishes
            leftOutAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    // this method is called when the animation first starts
                    flashcardAnswer.visibility = View.INVISIBLE
                    flashcardQuestion.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animation?) {
                    // this method is called when the animation is finished playing
                    flashcardQuestion.startAnimation(rightInAnim)

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

                    flashcardAnswer.visibility = View.INVISIBLE
                    flashcardQuestion.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // we don't need to worry about this method
                }
            })

            flashcardQuestion.startAnimation(leftOutAnim)

        }

    }

}