package es.uam.eps.dadm.cardsFranccySambrano

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import es.uam.eps.dadm.cardsFranccySambrano.database.CardDatabase

class CardDeleteViewModel  (application: Application): AndroidViewModel(application)  {
    private val context = getApplication<Application>().applicationContext


    val getContext = CardDatabase.getInstance(context).cardDao

}