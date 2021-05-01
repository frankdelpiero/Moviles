package es.uam.eps.dadm.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.ListItemCardBinding
import es.uam.eps.dadm.cards.databinding.ListItemDeckBinding
import timber.log.Timber

class DeckAdapter() : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {

    var data =  listOf<Deck>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var binding: ListItemDeckBinding

    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var deck:Deck
        fun bind(deck: Deck) {
            Timber.i("ER DECK es ${deck.name}")
            this.deck =deck
            binding.deck  = deck
        }
        init { // Navegacion desde deckListFragment a cardListFragment
            binding.studyDeck.setOnClickListener {
                val id = deck.id
                Timber.i("ID es $id")
                it.findNavController()
                        .navigate(DeckListFragmentDirections
                                .actionDeckListFragment2ToCardListFragment(id))
            }
        }

    }
    //Crea contenedores que va a reciclar la vista recicladora
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemDeckBinding.inflate(layoutInflater, parent, false)
        return DeckHolder(binding.root)
    }

    override fun getItemCount() = data.size //Obtenemos el tamaño de las cartas

    override fun onBindViewHolder(holder: DeckHolder, position: Int) {
        holder.bind(data[position])
    }
}