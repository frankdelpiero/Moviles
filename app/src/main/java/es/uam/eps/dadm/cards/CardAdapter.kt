package es.uam.eps.dadm.cards

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    private var holderCounter = 0
    private var onBindCounter = 0

    var data = listOf<Card>()

    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            holderCounter++;
            Timber.i("CardHolder number $holderCounter created")
        }
    }
    //Crea contenedores que va a reciclar la vista recicladora
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(TextView(parent.context))
    }

    override fun getItemCount() = data.size //Obtenemos el tamaño de las cartas

    override fun onBindViewHolder( //Muestran las vistas incluyendo las recicladas
        holder: CardHolder,
        position: Int
    ) {
        val item = data[position]
        (holder.itemView as TextView).text = item.question + "\n" + item.answer
        onBindCounter++;
        Timber.i("onBindViewHolder called $onBindCounter times")
    }
    }
}