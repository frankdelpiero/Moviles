
package es.uam.eps.dadm.cards

import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    var card:Card? = null
    var cards: MutableList<Card> = mutableListOf<Card>()
    init {
        //Timber.i("MainViewModel created")
        cards = CardsApplication.cards
        card = random_card()
    }

    fun update(quality: Int) {
        // Completa este método
       // _cardsLeft.value = cardsLeft.value?.minus(1)
        card?.quality = quality
        Timber.i("calidad ${card?.quality}")
        card?.update(LocalDateTime.now())
        card =  random_card()
    }
    private fun random_card() = try {
        cards.filter { card ->
            card.isDue(LocalDateTime.now())
        }.random()
    } catch (e: NoSuchElementException) {
        null
    }
    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }
}