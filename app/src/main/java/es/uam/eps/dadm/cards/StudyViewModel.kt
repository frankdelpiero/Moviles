
package es.uam.eps.dadm.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.time.LocalDateTime

class StudyViewModel : ViewModel() {
    var card:Card? = null
    var cards: MutableList<Card> = mutableListOf<Card>()
    private val _cardsLeft = MutableLiveData<Int>()
    val cardsLeft : LiveData<Int> // Guardo el numero de cartas restantes
        get() = _cardsLeft

    init {
        Timber.i("MainViewModel created")
        var deckList = CardsApplication.getDeck()
        for (itdeck in deckList){
            for (itcard in itdeck.cards){
                cards.add(itcard)
            }
        }
        _cardsLeft.value = CardsApplication.numberOfCardsLeft() //Numero de cartas restantes
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