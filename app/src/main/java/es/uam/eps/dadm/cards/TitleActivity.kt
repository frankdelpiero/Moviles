package es.uam.eps.dadm.cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.cards.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
        supportActionBar?.hide()
        var fragment = supportFragmentManager //Lista de fragmentos
                .findFragmentById(R.id.navHostFragment)

        if (fragment == null){ //En caso de que sea la primera vez que registramos el fragmento
            fragment = TitleFragment()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.navHostFragment, fragment)
                    .commit()
        }
    }
}
