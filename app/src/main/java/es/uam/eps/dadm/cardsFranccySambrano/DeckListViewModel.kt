
package es.uam.eps.dadm.cardsFranccySambrano

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cardsFranccySambrano.database.CardDatabase

class DeckListViewModel (application: Application)
    : AndroidViewModel(application){
    val context = getApplication<Application>().applicationContext
    //Obtener las referencias para obtener la lista de cartas
    val cardDao = CardDatabase.getInstance(context).cardDao

    val decks = cardDao.getDecks(Firebase.auth.currentUser.uid)


}

