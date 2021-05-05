package es.uam.eps.dadm.cardsFranccySambrano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.cardsFranccySambrano.databinding.ActivityStudyBinding
import timber.log.Timber

private const val TAG : String = "MainActivity"
private const val ANSWERED_KEY = "es.uam.eps.dadm.cards:answered"

class StudyActivity : AppCompatActivity() {
    lateinit var binding: ActivityStudyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study)

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