package com.example.swipequiz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
    }

    private fun initAdapter() {
        val quizList = mutableListOf(
            Quiz(R.string.question1, "question 1", false),
            Quiz(R.string.question2, "question 2", false),
            Quiz(R.string.question3, "question 3", false),
            Quiz(R.string.question4, "question 4", true)
        )

        val adapter = QuizAdapter(quizList){
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        ItemTouchHelper(SwipeToDeleteCallback(this, adapter)).attachToRecyclerView(recyclerView)
    }

    data class Quiz(val id: Int, val name: String, val isCorrect: Boolean)

    class SwipeToDeleteCallback(
        private val context: Context,
        private val quizAdapter: QuizAdapter
    ) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val swipeQuizQuestion = quizAdapter.quizList[position]
            quizAdapter.bounceItemBackAfterSwipe(position)


            if ((direction == ItemTouchHelper.LEFT && swipeQuizQuestion.isCorrect) ||
                (direction == ItemTouchHelper.RIGHT && !swipeQuizQuestion.isCorrect)
            ) {
                Toast.makeText(context, "Incorrect! The answer was: true!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "Correct! The answer was: false", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}
