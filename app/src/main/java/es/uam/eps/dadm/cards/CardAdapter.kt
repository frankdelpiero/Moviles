package es.uam.eps.dadm.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    private var holderCounter = 0
    private var onBindCounter = 0

    var data =  listOf<Card>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var card: Card
        private val questionTextView: TextView = itemView.findViewById(R.id.list_item_question)
        private val answerTextView:TextView = itemView.findViewById(R.id.list_item_answer)
        private val dateTextView: TextView = itemView.findViewById(R.id.list_item_date)
        init {
            itemView.setOnClickListener {
                Timber.d("Tarjeta: ${card.question} seleccionada")
            }
        }

        fun bind(card: Card) {
            this.card = card
            questionTextView.text = card.question
            answerTextView.text = card.answer
            dateTextView.text = card.date.substring(0,10)
        }
    }
    //Crea contenedores que va a reciclar la vista recicladora
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_card, parent, false)
        return CardHolder(view)
    }

    override fun getItemCount() = data.size //Obtenemos el tamaño de las cartas

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(data[position])
    }
}