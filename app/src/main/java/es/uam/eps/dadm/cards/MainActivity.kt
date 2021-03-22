package es.uam.eps.dadm.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import java.time.LocalDateTime
private const val TAG : String = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var answerButton: Button
    private lateinit var answerTextView: TextView
    private lateinit var difficiltyButtons:LinearLayout
    private lateinit var separatorView:View
    private lateinit var card:Card // Carta
    private lateinit var easyButton: Button
    private lateinit var doubtButton: Button
    private lateinit var hardButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        card = Card("Tree","Arbol")
        separatorView = findViewById(R.id.separator_view)
        difficiltyButtons = findViewById(R.id.difficultyButtons)
        answerButton = findViewById(R.id.answer_button) // El boton de ver la respuesta
        answerTextView = findViewById(R.id.answer_text_view) //La respuesta

        //Referencias de los botones
        easyButton = findViewById(R.id.easyButton)
        doubtButton = findViewById(R.id.doubtButton)
        hardButton = findViewById(R.id.difficultButton)


        answerButton.setOnClickListener {
            answerTextView.visibility = View.VISIBLE
            answerButton.visibility = View.INVISIBLE
            difficiltyButtons.visibility = View.VISIBLE
            separatorView.visibility = View.VISIBLE
        }

        //Escuchadores de los botones

        easyButton.setOnClickListener{
            card.quality = 5
            card.update(LocalDateTime.now())
        }

        doubtButton.setOnClickListener{
            card.quality = 3
            card.update(LocalDateTime.now())
            Log.d(TAG,"easiness: ${card.easiness}")
        }

        hardButton.setOnClickListener{
            card.quality = 0
            card.update(LocalDateTime.now())
        }

    }
}