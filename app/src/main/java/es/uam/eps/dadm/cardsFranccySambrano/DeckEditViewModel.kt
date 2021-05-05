package es.uam.eps.dadm.cardsFranccySambrano

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.cardsFranccySambrano.database.CardDatabase

class DeckEditViewModel (application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val deckId = MutableLiveData<Long>()


    val deck: LiveData<Deck> = Transformations.switchMap(deckId) {
        CardDatabase.getInstance(context).cardDao.getDeck(it)
    }

    val getContext = CardDatabase.getInstance(context).cardDao

    fun loadCardDeck(id: Long) {
        deckId.value = id
    }
}