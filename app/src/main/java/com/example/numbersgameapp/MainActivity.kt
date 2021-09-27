package com.example.numbersgameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var main_constraintLayout: ConstraintLayout
    lateinit var number_editText : EditText
    lateinit var main_recycleView: RecyclerView
    lateinit var Gusses: ArrayList<String>
    lateinit var guss_button: Button
    var number_OfAttempts = 3
    var random_number : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        random_number = Random.nextInt(0, 11)
        Gusses = ArrayList()
        main_constraintLayout = findViewById(R.id.main_constraintLayout)
        main_recycleView = findViewById(R.id.main_recycleView)
        main_recycleView.adapter = GussedNumberAdapter(this,Gusses)
        main_recycleView.layoutManager = LinearLayoutManager(this)

        number_editText = findViewById(R.id.number_editText)

        guss_button = findViewById<Button>(R.id.guss_button)
        guss_button.setOnClickListener { Guss_the_Number() }

    }

    fun Guss_the_Number(){
        if(number_editText.text.toString() == "")
            Snackbar.make(main_constraintLayout,"You should Enter data", Snackbar.LENGTH_LONG).show()
        else{
            val guessed_nuumber = number_editText.text.toString().toInt()

            if(number_OfAttempts > 0){
                number_OfAttempts--
                if(guessed_nuumber == random_number)
                    Gusses.add("You Win!!")
                else{
                    Gusses.add("You guessed $guessed_nuumber")
                    Gusses.add("You have $number_OfAttempts guesses left")
                }
            } else{
                Gusses.add("You lose :( \nThe correct number is : $random_number")
            }
            number_editText.text.clear()
            number_editText.clearFocus()
        }
    }
}