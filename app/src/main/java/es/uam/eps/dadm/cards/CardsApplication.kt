package es.uam.eps.dadm.cards

import android.app.Application
import timber.log.Timber
import java.time.LocalDateTime



class CardsApplication: Application() {
    init{
        cards.add(Card("To wake up", "Despertarse"))
        cards.add(Card("To give in", "Dar el brazo a torcer"))
        cards.add(Card("To pick up","Recoger"))
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }

    companion object {
        fun numberOfCardsLeft(): Int {
            Timber.i("BOXBOXBOX")
            for (card in cards){
                Timber.i("Cartas: ${card.question} ${card.answer}  ${card.nextPracticeDate} ${card.easiness}")
                if (card.isDue(LocalDateTime.now())){

                    return 1
                }
            }
            return 0
        }

        var cards: MutableList<Card> = mutableListOf<Card>()

    }

}