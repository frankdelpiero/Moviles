package es.uam.eps.dadm.cardsFranccySambrano

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cardsFranccySambrano.database.CardDatabase

class CardListViewModel(application: Application)
    : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext
    //Obtener las referencias para obtener la lista de cartas
    val cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards(Firebase.auth.currentUser.uid!!)

    val getContext = CardDatabase.getInstance(context).cardDao


}