package es.uam.eps.dadm.cardsFranccySambrano

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.cardsFranccySambrano.database.CardDatabase

class CardEditViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val cardId = MutableLiveData<String>()


    val card: LiveData<Card> = Transformations.switchMap(cardId) {
        CardDatabase.getInstance(context).cardDao.getCard(it)
    }

    val getContext = CardDatabase.getInstance(context).cardDao

    fun loadCardId(id: String) {
        cardId.value = id
    }
}