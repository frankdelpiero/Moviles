
package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.*
import es.uam.eps.dadm.cards.database.CardDatabase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

class  StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val executor = Executors.newSingleThreadExecutor()
    private val context = getApplication<Application>().applicationContext
    var card:Card? = null
    //var cards: MutableList<Card> = mutableListOf<Card>()
    var cards:LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards() //Lista de cartas
    var dueCard: LiveData<Card?> =
        Transformations.map(cards, ::due) // Siguiente carta
    private fun due(cards: List<Card>) = try { // Aplica el metdo random
        cards.filter { card -> card.isDue(LocalDateTime.now()) }.random()
    } catch (e: Exception) {
        null
    }
    //private val _cardsLeft = MutableLiveData<Int>()
   // val cardsLeft : LiveData<Int> // Guardo el numero de cartas restantes
   //     get() = _cardsLeft
    var cardsLeft: LiveData<Int> =
        Transformations.map(cards, ::left)

    private fun left(cards: List<Card>) =
        cards.filter { card -> card.isDue(LocalDateTime.now()) }.size

    init {
        Timber.i("MainViewModel created")
        //var deckList = CardsApplication.getDeck()
        //for (itdeck in deckList){
        //    for (itcard in itdeck.cards){
        //        cards.add(itcard)
        //    }
       // }
      //  _cardsLeft.value = CardsApplication.numberOfCardsLeft() //Numero de cartas restantes
       // card = random_card()
    }

    fun update(quality: Int) {
        // Completa este método
        //_cardsLeft.value = cardsLeft.value?.minus(1)
        card?.quality = quality
        card?.update(LocalDateTime.now())
        executor.execute {
            CardDatabase.getInstance(context).cardDao.update(card!!) // Aplicamos el metodo update
        }
       // card =  random_card()
    }
    //private fun random_card() = try {
      //  cards.filter { card ->
      //      card.isDue(LocalDateTime.now())
      //  }.random()
    //} catch (e: NoSuchElementException) {
    //    null
   // }
    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }
}