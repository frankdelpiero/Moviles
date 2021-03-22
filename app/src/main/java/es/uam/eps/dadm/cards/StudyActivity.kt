package es.uam.eps.dadm.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.cards.databinding.ActivityStudyBinding
import timber.log.Timber

private const val TAG : String = "MainActivity"
private const val ANSWERED_KEY = "es.uam.eps.dadm.cards:answered"

class StudyActivity : AppCompatActivity() {
    lateinit var binding: ActivityStudyBinding

    private val viewModel: MainViewModel by lazy { //Instancia de view model
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var listener = View.OnClickListener { v ->
        // Asigana a quality el valor 0, 3 o 5,
        // dependiendo del botón que se haya pulsado
        val quality = when (v?.id) {
            R.id.easyButton -> 5
            R.id.doubtButton -> 3
            R.id.difficultButton -> 0
            else -> throw Exception("Unavailable quality")
        }
        // Llama al método update de viewModel
        viewModel.update(quality)
        // Si la propiedad card de viewModel es null
        // informa al usuario mediante un Toast de que
        // no quedan tarjetas
            if (viewModel.card == null){
                Toast.makeText(this,R.string.No_cards,Toast.LENGTH_LONG).show()
            }
        binding.invalidateAll()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study)

        val observer = object: Observer<Int> {
            override fun onChanged(t: Int?) {
                binding.infoTextView?.text = t.toString()
            }
        }
        // Incluyo la interaccion con cardsLeft
        //viewModel.cardsLeft.observe(this, observer)

        binding.mainViewModel = viewModel // Va a usar view model
        binding.apply {
            answerButton.setOnClickListener {
                viewModel.card?.answered = true
                binding.invalidateAll()
            }
            binding.easyButton.setOnClickListener(listener)
            binding.doubtButton.setOnClickListener(listener)
            binding.difficultButton.setOnClickListener(listener)
        }

        Timber.i("onCreate called")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
        binding.invalidateAll()
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState called")
       //outState.putBoolean(ANSWERED_KEY, viewModel.card?.answered)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.i("onRestoreInstanceState called")
        //viewModel.card?.answered = savedInstanceState.getBoolean(ANSWERED_KEY) ?: false
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called")
    }
}