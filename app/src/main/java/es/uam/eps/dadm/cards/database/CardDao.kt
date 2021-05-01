package es.uam.eps.dadm.cards.database

import androidx.lifecycle.LiveData
import androidx.room.*
import es.uam.eps.dadm.cards.Card
import es.uam.eps.dadm.cards.Deck

@Dao
interface CardDao {
    @Query("SELECT * FROM cards_table")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id: String): LiveData<Card> //He quitado la interrogacion

    @Query("SELECT * FROM cards_table WHERE deckId= :id")
    fun getTypeDeckCard(id:Long): LiveData<List<Card>>

    @Insert
    fun addCard(card: Card)

    @Update
    fun update(card: Card)

    @Delete
    fun delete(card: Card)

    @Delete
    fun deleteDeck(deck: Deck)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Query("SELECT * FROM decks_table")
    fun getDecks(): LiveData<List<Deck>>

    @Query("SELECT * FROM decks_table WHERE id = :id")
    fun getDeck(id: Long): LiveData<Deck> //He quitado la interrogacion

}