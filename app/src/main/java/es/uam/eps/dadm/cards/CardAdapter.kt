package es.uam.eps.dadm.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.ListItemCardBinding
import timber.log.Timber

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {

    var data =  listOf<Card>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var dataDeck = listOf<String>()

    lateinit var binding: ListItemCardBinding

   inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var card: Card
       lateinit var deckId:  String
        fun bind(card: Card,deckId:String) {
            this.card=card
            this.deckId=deckId
            binding.card = card
        }
       init { // Navegacion desde cardListFragment a cardEditFragment
           binding.listItemQuestion.setOnClickListener {
               val id = card.id
               val deckId = deckId
               it.findNavController()
                   .navigate(CardListFragmentDirections
                       .actionCardListFragmentToCardEditFragment(id,deckId))
           }
       }

    }
    //Crea contenedores que va a reciclar la vista recicladora
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemCardBinding.inflate(layoutInflater, parent, false)
        binding.apply {
            //Mostrar informacion extra
            listItemButtonMore.setOnClickListener{
                listItemQuestion.visibility = View.INVISIBLE
                listItemAnswer.visibility = View.INVISIBLE
                listItemDate.visibility = View.INVISIBLE

                listItemButtonMore.visibility = View.INVISIBLE
                listItemButtonLess.visibility = View.VISIBLE

                listItemEasiness.visibility = View.VISIBLE
                listItemRepetitions.visibility = View.VISIBLE
                listItemInterval.visibility = View.VISIBLE
            }
            //Ocultar informacion extra
            listItemButtonLess.setOnClickListener{
                listItemQuestion.visibility = View.VISIBLE
                listItemAnswer.visibility  = View.VISIBLE
                listItemDate.visibility = View.VISIBLE

                listItemButtonMore.visibility = View.VISIBLE
                listItemButtonLess.visibility = View.INVISIBLE

                listItemEasiness.visibility = View.INVISIBLE
                listItemRepetitions.visibility = View.INVISIBLE
                listItemInterval.visibility = View.INVISIBLE
            }
        }
        return CardHolder(binding.root)
    }

    override fun getItemCount() = data.size //Obtenemos el tamaño de las cartas

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(data[position],dataDeck[0])
    }
}