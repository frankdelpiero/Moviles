package es.uam.eps.dadm.cards

import android.app.Application
import androidx.annotation.Nullable
import es.uam.eps.dadm.cards.database.CardDatabase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors


class CardsApplication: Application() {
    private val executor = Executors.newSingleThreadExecutor() // Hilo para ejecutar consultas en segundo plano
    init{ //Delete this
        //decks.add(Englishdeck)
        //decks.add(FrenchDeck)
        //Englishdeck.cards.add(Card("To wake up", "Despertarse"))
        //Englishdeck.cards.add(Card("To give in", "Dar el brazo a torcer"))
        //Englishdeck.cards.add(Card("To pick up","Recoger"))
        //FrenchDeck.cards.add(Card("Bonjour","hola"))
        //FrenchDeck.cards.add(Card("Adieu","Adios"))
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        val cardDatabase = CardDatabase.getInstance(context = this)
        executor.execute{
            cardDatabase.cardDao.addDeck(Deck(1, "Inglés"))
            cardDatabase.cardDao.addDeck(Deck(2, "Francés"))
            cardDatabase.cardDao.addDeck(Deck(3,"Japones"))
            cardDatabase.cardDao.addCard(Card("To wake up", "Despertarse", deckId = 1))
            cardDatabase.cardDao.addCard(Card("To give in", "Dar el brazo a torcer", deckId = 1))
            cardDatabase.cardDao.addCard(Card("Coche", "Voiture", deckId = 2))
            cardDatabase.cardDao.addCard(Card("Arigato", "Gracias", deckId = 3))
        }

    }

    companion object { //Delete this
        fun numberOfCardsLeft(): Int {
            var counter=0
               /** for (d in decks){
                    for (c in d.cards){
                        if (c.isDue(LocalDateTime.now())){
                            counter+=1
                        }
                    }
                }*/
            return counter
        }
        /**fun getCard(id:String,idDeck: Long):Card{
           /** var deck = getIdDeck(idDeck)
            for (card in deck.cards){
                if (card.id == id){
                    return card
                }
            }*/
            return Card("NO","existe",idDeck)
        }

        fun getIdDeck(idDeck:Long):Deck{

           /** for (d in decks){
                if (d.id == idDeck){
                    return d
                }
            }
            return Deck("NO")*/

            return Deck(idDeck,"e")
        }

        fun getDeck():MutableList<Deck>{
            return decks
        }

        fun addCard(card:Card){
            cards.add(card)
        }

        fun addDeck(deck:Deck){
            decks.add(deck)
        }
        /**fun deleteCard(id:String){
            var cardToRemove = getCard(id,"e") //Provisional
            cards.remove(cardToRemove)
        }*/

        var cards: MutableList<Card> = mutableListOf<Card>()

        var decks: MutableList<Deck> = mutableListOf<Deck>()
       // var Englishdeck:Deck = Deck("Mazo Ingles")
        //var FrenchDeck = Deck("Mazo Frances")*/
    }

}