package es.uam.eps.dadm.cardsFranccySambrano.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.uam.eps.dadm.cardsFranccySambrano.Card
import es.uam.eps.dadm.cardsFranccySambrano.Deck

@Database(entities = [Card::class,Deck::class], version = 7, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {
    abstract val cardDao: CardDao // Acceso a la interfaz
    companion object { // Objeto acompañante
        @Volatile
        private var INSTANCE: CardDatabase? = null // Instancia

        fun getInstance(context: Context): CardDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder( // Obtenemos la base de datos
                    context.applicationContext, //COntexto de la aplicacion
                    CardDatabase::class.java,   // Clase de la BD
                    "cards_database"      // Database name
                )
                    .fallbackToDestructiveMigration() // Para evitar problemas
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}