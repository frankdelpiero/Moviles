package es.uam.eps.dadm.cards
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "decks_table")
class Deck(
    @PrimaryKey
    var id: Long,
    @ColumnInfo(name = "deck_name")
    var name:String
) {
    //var cards: MutableList<Card> = mutableListOf()
/**
    fun addCard(card:Card){

        /**
         *  println("Añadiendo tarjeta al mazo $name")

         *         var purpose:Int?
         * do {
            println("Tecela el tipo (0 -> Card ; 1->Cloze): ")
            purpose = readLine()?.toIntOrNull()?:0 //Si no se coloca nada por defecto sera 0
        }while (purpose!! >= 2)

        print("Teclea la pregunta: ")
        var question =  readLine()!!.toString()
        print("Teclea la respuesta: ")
        var answer = readLine()!!.toString()

        when (purpose) {
            0 -> {
                cards.add(Card(question,answer))
            }
            else ->{
                cards.add(Cloze(question,answer))
            }
        }
        */
        //cards.add(Card(card.question,card.answer))
        println("Tarjeta añadida correctamente")

    }

    fun listCards(){
        for (i in cards){
            println("${i.question} -> ${i.answer}")
        }
    }

    fun simulate(period: Int){
        println("Simulacion del mazo $name: ")
        var now = LocalDateTime.now()
        for (i in 0..period){
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            println("Fecha: "+formatter.format(now))
            for (card in cards){
                if (card.isDue(now)){
                    card.cardDetails()
                }
            }
            now = now.plusDays(1) //Incremento del dia
        }
    }

  //  fun cloneDeck(deckOriginal:Deck){
   //     deckOriginal.cards.forEach{
   //         if (it.javaClass.toString() == "class Cloze"){
   //             cards.add(Cloze(it.question,it.answer))
   //         }else {
  //              cards.add(Card(it.question,it.answer))
  //          }

  //      }
 //   }

    fun removeDeck(){
        cards.removeAll(cards)
    }

    fun writeCards(name: String){
        val archivador = File("data/deck")
        if(!archivador.exists()){
            archivador.mkdirs() //Se crea el archivador
        }

        val rutaFichero = "data/deck/$name"
        val archivo = File(rutaFichero)
        if (!archivo.exists()){
            archivo.createNewFile()
        }
        File(rutaFichero).printWriter().use {
                out ->
            for (c in cards){
                out.println(c.toString())
            }
        }
    }

    fun fromString(){
        return
    }
    fun readCards(name:String){
        try{
            val fichero = File("data/deck/$name").readLines()
            for (linea in fichero){
                var identificador = linea.split("|")
                if (identificador[0] == "Card"){
                    cards.add(Card.fromString(linea))
                } else{
                    cards.add(Cloze.fromString(linea))
                }
            }
        }catch (e:Exception){
            println("Fichero no alojado")
        }
    }

    fun deckSize():String{
        var counter = 0
        for (c in cards){
            if (c.isDue(LocalDateTime.now())) {
                counter += 1
            }
        }
        return "Cartas por estudiar: "+counter
    }

*/
}
