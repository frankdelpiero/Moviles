package es.uam.eps.dadm.cards

import android.app.Application
import timber.log.Timber


class CardsApplication: Application() {
    init{
        cards.add(Card("To wake up", "Despertarse"))
        cards.add(Card("To give in", "Dar el brazo a torcer"))
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }
    companion object {
        var cards: MutableList<Card> = mutableListOf<Card>()
    }

}