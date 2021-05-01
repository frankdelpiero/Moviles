package es.uam.eps.dadm.cards

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import timber.log.Timber
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.ceil
import kotlin.math.max
import java.text.SimpleDateFormat
private const val TAG : String = "Card"
@Entity(tableName = "cards_table")
open class Card(
    @ColumnInfo(name = "card_question")
    open var question: String,
    @ColumnInfo(name = "card_answer")
    open var answer: String,
    var deckId:Long,
    @ColumnInfo(name = "card_date")
    var date: String = LocalDateTime.now().toString(),
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
){
    var quality :Int =0
    var repetitions: Int = 0
    var interval: Long = 1L
    var nextPracticeDate:String = LocalDateTime.now().toString()
    var easiness: Double = 2.5
    //var nowaux:LocalDateTime = LocalDateTime.now()
    var answered = false
    open fun show(){

        /**var respuestaDificultad =0
        println("$question (INTRO para ver la respuesta)")
        respuestaDificultad = readLine()?.toIntOrNull()?:1
        do{
        print("${answer.capitalize()} (Teclea 0 -> Difícil 3 -> Dudo 5 -> Fácil): 3) ")
        respuestaDificultad =  readLine()?.toIntOrNull() ?: 1
        }while(respuestaDificultad != 0 && respuestaDificultad != 3 && respuestaDificultad != 5)

        respuestaDificultad.also { quality = it }
         */

    }


    //Actualizar cuando se mostrara la siguiente fecha
    fun update(currentDate:LocalDateTime){
        easiness = max(1.3,(easiness+0.1-(5-quality)*(0.08+(5-quality)*0.02) ))
        if (quality < 3) repetitions = 0 else repetitions += 1
        if (repetitions <=1){
            interval = 1
        } else if (repetitions == 2){
            interval = 6
        } else {
            interval = ceil(interval.toDouble()*easiness).toLong()
        }
        Timber.i("Easiness: $easiness")
        nextPracticeDate = currentDate.plusDays(interval).toString() //Asignamos el siguiente dia
    }

    fun details(){
        println("eas: ${"%2.2f".format(easiness)} rep: $repetitions int:$interval next = $nextPracticeDate")

    }


    open fun simulate(now:LocalDateTime) {
        /**println()
        nowaux = now
        if (now.dayOfYear >= nextPracticeDate.dayOfYear) {
        println("Simulación de la tarjeta $question:")
        show()
        }*/
    }

    override fun toString(): String {
        return "Card|$question|$answer|$date|$id|$easiness|$repetitions|$interval|$nextPracticeDate"
    }

    fun cardDetails(){
        show()
        update(LocalDateTime.parse(nextPracticeDate.toString()))
        details()
        println("")

    }

    fun isDue(date:LocalDateTime) = date.dayOfYear >= LocalDateTime.parse(nextPracticeDate).dayOfYear

    fun establecerDetalles(pdate: String,pid:String,peasiness:String,prepetitions:String,pinterval:String,pnextPracticeDay:String){
        date = pdate
        id = pid
        easiness = peasiness.toDoubleOrNull()?:2.5
        repetitions = prepetitions.toIntOrNull()?:0
        interval = pinterval.toLongOrNull()?:1L
        nextPracticeDate = pnextPracticeDay
    }

    companion object {
        fun fromString(cad:String):Card{
            var listacadena = cad.split("|")
            var questionP = listacadena[1]
            var answerP = listacadena[2]
            var c =Card(questionP,answerP,5) // CAMBIAR
            println("${c.question} , ${c.answer}")
            c.establecerDetalles(listacadena[3],listacadena[4],listacadena[5],listacadena[6],listacadena[7],listacadena[8])
            return c
        }

    }

    fun update_easy() {
        Log.d(TAG, "EASY")
        quality = 5
        update(LocalDateTime.now())
    }

    fun update_doubt() {
        quality = 3
        Log.d(TAG, "MEDIUM")
        update(LocalDateTime.now())
    }

    fun update_difficult() {
        quality = 0
        Log.d(TAG, "HARD")
        update(LocalDateTime.now())
    }

    fun showMoreDetails(detail:Int):String{
        var cadena = ""
        if (detail == 1){
            cadena = "Easiness: "+ this.easiness.toString().substring(0,3)
        } else if (detail==2){
            cadena = "Repetitions: "+this.repetitions
        } else if (detail == 3){
            cadena = "Interval: "+this.interval
        }
        return cadena
    }

}