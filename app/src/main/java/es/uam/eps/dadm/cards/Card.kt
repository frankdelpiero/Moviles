package es.uam.eps.dadm.cards

import android.util.Log
import android.view.View
import android.widget.Toast
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.ceil
import kotlin.math.max
import java.text.SimpleDateFormat
private const val TAG : String = "Card"
open class Card(
        open var question: String,
        open var answer: String,
        var date: String = LocalDateTime.now().toString(),
        var id: String = UUID.randomUUID().toString()
){
    var quality :Int =0
    var repetitions: Int = 0
    var interval: Long = 1L
    var nextPracticeDate: LocalDateTime = LocalDateTime.now()
    var easiness: Double = 2.5
    var nowaux:LocalDateTime = LocalDateTime.now()
    var answered = false

    open fun show(){
        var respuestaDificultad :Int?
        println("$question (INTRO para ver la respuesta)")
        respuestaDificultad = readLine()?.toIntOrNull()?:1
        respuestaDificultad = 3
        do{
            print("${answer.capitalize()} (Teclea 0 -> Difícil 3 -> Dudo 5 -> Fácil): 3) ")
            respuestaDificultad =  readLine()?.toIntOrNull() ?: 1
        }while(respuestaDificultad != 0 && respuestaDificultad != 3 && respuestaDificultad != 5)

        respuestaDificultad.also { quality = it }


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
        nextPracticeDate = currentDate.plusDays(interval) //Asignamos el siguiente dia
    }

    fun details(){
        println("eas: ${"%2.2f".format(easiness)} rep: $repetitions int:$interval next = $nextPracticeDate")

    }


    open fun simulate(now:LocalDateTime) {
        println()
        println("Hoy es -->"+now)
        nowaux = now
        if (now.dayOfYear >= nextPracticeDate.dayOfYear) {
            println("Simulación de la tarjeta $question:")
            show()
        }
    }

    override fun toString(): String {
        return "Card|$question|$answer|$date|$id|$easiness|$repetitions|$interval|$nextPracticeDate"
    }

    fun cardDetails(date:LocalDateTime):Boolean{
        if (date.dayOfYear >= nextPracticeDate.dayOfYear){
            show()
            update(LocalDateTime.parse(nextPracticeDate.toString()))
            details()
            println("")
            return true
        }
        return false
    }

    fun isDue(date:LocalDateTime) = date.dayOfYear >= nextPracticeDate.dayOfYear

    fun establecerDetalles(pdate: String,pid:String,peasiness:String,prepetitions:String,pinterval:String,pnextPracticeDay:String){
        date = pdate
        id = pid
        easiness = peasiness.toDoubleOrNull()?:2.5
        repetitions = prepetitions.toIntOrNull()?:0
        interval = pinterval.toLongOrNull()?:1L
        nextPracticeDate = LocalDateTime.parse(pnextPracticeDay, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnnnn"))
    }

    companion object {
        fun fromString(cad:String):Card{
            var listacadena = cad.split("|")
            var questionP = listacadena[1]
            var answerP = listacadena[2]
            var c =Card(questionP,answerP)
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

}