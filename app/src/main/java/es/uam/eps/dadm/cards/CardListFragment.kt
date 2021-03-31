package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding
import timber.log.Timber
import java.time.LocalDateTime

class CardListFragment: Fragment(){
    var estudios:String = ""
    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardAdapter // Instancio el adaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCardListBinding>(
                inflater,
                R.layout.fragment_card_list,
                container,
                false)
        adapter = CardAdapter() // Creamos el adaptador
        adapter.data = CardsApplication.cards
        binding.cardRecyclerView.adapter = adapter

        binding.buttonQuestion.setOnClickListener { view ->
            if (CardsApplication.numberOfCardsLeft()  > 0)
                view.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment2)
            else
                Toast.makeText(activity, R.string.No_cards, Toast.LENGTH_SHORT).show()
        }

        return binding.root


    }

}