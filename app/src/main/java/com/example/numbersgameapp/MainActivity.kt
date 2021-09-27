package com.example.numbersgameapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var main_constraintLayout: ConstraintLayout
    private lateinit var number_editText : EditText
    private lateinit var main_recycleView: RecyclerView
    private lateinit var Gusses: ArrayList<String>
    private lateinit var guss_button: Button
    private var number_OfAttempts = 3
    private var random_number : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            random_number = savedInstanceState.getInt("random_number")
            number_OfAttempts = savedInstanceState.getInt("number_OfAttempts")
            Gusses = savedInstanceState.getStringArrayList("guesses") as ArrayList<String>
        } else {
            random_number = Random.nextInt(0, 11)
            Gusses = ArrayList()
        }

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
            number_OfAttempts--
            if(number_OfAttempts > 0){
                if(guessed_nuumber == random_number) {
                    val msg = "You Win!!"
                    Gusses.add(msg)
                    replayAlert(msg+",\nPlay again?")
                    endOfTheGAme()
                } else{
                    Gusses.add("You guessed $guessed_nuumber")
                    Gusses.add("You have $number_OfAttempts guesses left")
                }
            } else{
                val msg = "You lose :( \nThe correct number is : $random_number"
                Gusses.add(msg)
                endOfTheGAme()
                replayAlert(msg + ",\nPlay again?")
            }
            main_recycleView.adapter?.notifyDataSetChanged()
            number_editText.text.clear()
            number_editText.clearFocus()
        }
    }

    private fun endOfTheGAme(){
        number_editText.isEnabled = false
        guss_button.isEnabled = false
        number_editText.isClickable = false
        guss_button.isClickable =false
    }

    private fun replayAlert(message: String){

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            // positive button
            .setPositiveButton("play", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            // negative button
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Game Over!")
        alert.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("number_OfAttempts", number_OfAttempts)
        outState.putInt("random_number", random_number)
        outState.putStringArrayList("guesses", Gusses)
    }
}