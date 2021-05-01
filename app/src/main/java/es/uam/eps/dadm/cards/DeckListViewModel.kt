
package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import es.uam.eps.dadm.cards.database.CardDatabase

class DeckListViewModel (application: Application)
    : AndroidViewModel(application){
    val context = getApplication<Application>().applicationContext
    //Obtener las referencias para obtener la lista de cartas
    val cardDao = CardDatabase.getInstance(context).cardDao

    val decks = cardDao.getDecks()

}

