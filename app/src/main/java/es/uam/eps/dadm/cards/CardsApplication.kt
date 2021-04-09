package es.uam.eps.dadm.cards

import android.app.Application
import androidx.annotation.Nullable
import timber.log.Timber
import java.time.LocalDateTime



class CardsApplication: Application() {
    init{
        decks.add(Englishdeck)
        decks.add(FrenchDeck)
        Englishdeck.cards.add(Card("To wake up", "Despertarse"))
        Englishdeck.cards.add(Card("To give in", "Dar el brazo a torcer"))
        Englishdeck.cards.add(Card("To pick up","Recoger"))
        FrenchDeck.cards.add(Card("Bonjour","hola"))
        FrenchDeck.cards.add(Card("Adieu","Adios"))
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }

    companion object {
        fun numberOfCardsLeft(): Int {
            var counter=0
                for (d in decks){
                    for (c in d.cards){
                        if (c.isDue(LocalDateTime.now())){
                            counter+=1
                        }
                    }
                }
            return counter
        }
        fun getCard(id:String,idDeck: String):Card{
            var deck = getIdDeck(idDeck)
            for (card in deck.cards){
                if (card.id == id){
                    return card
                }
            }
            return Card("NO","existe")
        }
        fun addCard(card:Card,idDeck:String){
            for (d in decks){
                if (d.id == idDeck){
                    d.cards.add(card) // Consigo la carta asociada a un almacen en especifico
                }
            }
        }

        fun getIdDeck(idDeck:String):Deck{
            for (d in decks){
                if (d.id == idDeck){
                    return d
                }
            }
            return Deck("NO")
        }

        fun getDeck():MutableList<Deck>{
            return decks
        }

        var decks: MutableList<Deck> = mutableListOf<Deck>()
        var Englishdeck:Deck = Deck("Mazo Ingles")
        var FrenchDeck = Deck("Mazo Frances")
    }

}