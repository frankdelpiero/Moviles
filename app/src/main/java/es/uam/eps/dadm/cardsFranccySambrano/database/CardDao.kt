package es.uam.eps.dadm.cardsFranccySambrano.database

import androidx.lifecycle.LiveData
import androidx.room.*
import es.uam.eps.dadm.cardsFranccySambrano.Card
import es.uam.eps.dadm.cardsFranccySambrano.Deck

@Dao
interface CardDao {
    @Query("SELECT * FROM cards_table WHERE userid = :userID")
    fun getCards(userID: String): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id: String): LiveData<Card> //He quitado la interrogacion

    @Query("SELECT * FROM cards_table WHERE deckId= :id and userid = :userID")
    fun getTypeDeckCard(id:Long,userID:String): LiveData<List<Card>>

    @Insert
    fun addCard(card: Card)

    @Update
    fun update(card: Card)

    @Update
    fun updateDeck(deck:Deck)
    @Delete
    fun delete(card: Card)

    @Delete
    fun deleteDeck(deck: Deck)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Query("SELECT * FROM decks_table WHERE deck_user = :userID")
    fun getDecks(userID: String): LiveData<List<Deck>>

    @Query("SELECT * FROM decks_table WHERE id = :id and deck_user = :userID")
    fun getDeck(id: Long,userID: String): LiveData<Deck> //He quitado la interrogacion


    @Query("DELETE FROM decks_table WHERE deck_name = :name and deck_user = :userID")
    fun deleteDeckByName(name: String,userID: String)

    @Query("DELETE FROM cards_table WHERE cards_table.card_question = :name and cards_table.deckId = :idDeck and userid= :userID")
    fun deleteCardOfDeck(name:String,idDeck: Long,userID: String)

    @Query("SELECT  decks_table.id FROM decks_table WHERE decks_table.deck_name = :name ")
    fun getIDdeck(name: String):Long

    @Query("DELETE FROM cards_table WHERE deckId = :idDeck and userid = :userID")
    fun deleteAllDecksById(idDeck:Long,userID: String)

    //@Query("DELETE  FROM  (cards_table join decks_table ON  cards_table.deckId =  decks_table.id)  where deck_name = :name")
    //fun deleteCardsOfDeck(name:String)


}