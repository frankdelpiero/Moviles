package es.uam.eps.dadm.cards

import java.time.LocalDateTime

//Clase heredada de Card
class Cloze( override var question: String, override var answer: String,):Card(question,answer) {

    override fun show(){

        /**var respuestaDificultad :Int?
        println("$question (INTRO para ver la respuesta)")
        respuestaDificultad = readLine()?.toIntOrNull()?:1
        do{
            var subdivision = question.split("*")
            if (subdivision.indices.last<2){
                println("La pregunta no tiene los * suficientes")
                return
            }
            var respuestaModificada = subdivision[0]+answer+subdivision[2]
            print("${respuestaModificada} (Teclea 0 -> Difícil 3 -> Dudo 5 -> Fácil): 3) ")
            respuestaDificultad =  readLine()?.toIntOrNull() ?: 1
        }while(respuestaDificultad != 0 && respuestaDificultad != 3 && respuestaDificultad != 5)
        respuestaDificultad.also { quality = it }*/
    }
    override fun toString(): String {
        return "Cloze|$question|$answer|$date|$id|$easiness|$repetitions|$interval|$nextPracticeDate"
    }



    companion object {
        fun fromString(cad:String):Card{
            var listacadena = cad.split("|")
            var questionP = listacadena[1]
            var answerP = listacadena[2]
            val c =Card(questionP,answerP)
            c.establecerDetalles(listacadena[3],listacadena[4],listacadena[5],listacadena[6],listacadena[7],listacadena[8])
            return c
        }

    }


}