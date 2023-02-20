package com.brywal07.itc362_hw3_ex1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.brywal07.itc362_hw3_ex1.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isClicked = false
    private var numCorrect = 0.0
    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false))
    private var currentIndex = 0
    private var currentQuestion = questionBank[currentIndex]
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called)")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.trueButton.setOnClickListener { view: View ->
            if(currentIndex==questionBank.size - 1){
                currentQuestion.isAnswered = true
                checkAnswer(true)
                var score = String.format("%.1f",getScore())
                score = score.plus(" %")
                Toast.makeText(this, "Your score is: ".plus(score), Toast.LENGTH_LONG).show()
                updateQuestion()
                reset()
            }else {
                currentQuestion.isAnswered = true
                checkAnswer(true)
                updateQuestion()
            }
        }
        binding.falseButton.setOnClickListener { view: View ->
            if(currentIndex==questionBank.size - 1){
                currentQuestion.isAnswered = true
                checkAnswer(false)
                var score = String.format("%.1f",getScore())
                score = score.plus(" %")
                Toast.makeText(this, "Your score is: ".plus(score), Toast.LENGTH_LONG).show()
                updateQuestion()
                reset()

            }else {
                currentQuestion.isAnswered = true
                checkAnswer(false)
                updateQuestion()
            }


        }
        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            currentQuestion=questionBank[currentIndex]
            updateQuestion()
        }
        binding.previousButton.setOnClickListener{
            if(currentIndex > 0) {
                currentIndex = (currentIndex - 1) % questionBank.size
                currentQuestion=questionBank[currentIndex]
                updateQuestion()
            }else{
                Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show()
            }
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(Bundle?) called)")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume(Bundle?) called)")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause(Bundle?) called)")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop(Bundle?) called)")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy(Bundle?) called)")
    }
    private fun updateQuestion(){
        binding.trueButton.isEnabled = !currentQuestion.isAnswered
        binding.falseButton.isEnabled = !currentQuestion.isAnswered
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if(userAnswer == correctAnswer){
            R.string.correct_snack
        }else{
            R.string.incorrect_snack
        }
        if (userAnswer == correctAnswer){
            numCorrect ++
        }else{
            numCorrect = numCorrect
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
    private fun getScore():Double{
        return (numCorrect / questionBank.size)*100
    }
    private fun reset(){
        for (i in questionBank){
            i.isAnswered=false
            numCorrect = 0.0
        }
    }
}