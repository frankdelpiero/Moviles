package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding
import timber.log.Timber
import java.time.LocalDateTime

class CardListFragment: Fragment(){
    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardAdapter // Instancio el adaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentCardListBinding>(
                 inflater,
                 R.layout.fragment_card_list,
                 container,
                 false)
        var args = CardListFragmentArgs.fromBundle(requireArguments())
        var deck = CardsApplication.getIdDeck(args.idMazo)
         adapter = CardAdapter() // Creamos el adaptador
         adapter.data = deck.cards // Lista de cartas de un almacen en especifico
        adapter.dataDeck = mutableListOf(args.idMazo) //ID de la carta
         binding.cardRecyclerView.adapter = adapter // Adaptador de las cartas

         /**binding.buttonQuestion.setOnClickListener { view ->
             if (CardsApplication.numberOfCardsLeft()  > 0)
                 view.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment2)
             else
                 Toast.makeText(activity, R.string.No_cards, Toast.LENGTH_SHORT).show()
         }*/
        //Escuchador que agrega una nueva carta y accede al panel para editarlo
        binding.newCardFab.setOnClickListener{
            val card = Card("","")
            CardsApplication.addCard(card,deck.id) // Añado la carta a un ID en especifico
            it.findNavController().navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id,deck.id))
        }


        return binding.root


    }

}