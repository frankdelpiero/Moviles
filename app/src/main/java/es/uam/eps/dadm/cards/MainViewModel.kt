
package es.uam.eps.dadm.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    var card:Card? = null
    var cards: MutableList<Card> = mutableListOf<Card>()
    private val _cardsLeft = MutableLiveData<Int>()
    val cardsLeft : LiveData<Int> // Guardo el numero de cartas restantes
        get() = _cardsLeft

    init {
        //Timber.i("MainViewModel created")
        cards = CardsApplication.cards
        _cardsLeft.value = cards.size
        card = random_card()
    }

    fun update(quality: Int) {
        // Completa este método
        _cardsLeft.value = cardsLeft.value?.minus(1)
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