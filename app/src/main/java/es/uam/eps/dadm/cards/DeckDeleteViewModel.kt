package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.cards.database.CardDatabase

class DeckDeleteViewModel  (application: Application): AndroidViewModel(application)  {
    private val context = getApplication<Application>().applicationContext


    val getContext = CardDatabase.getInstance(context).cardDao

}