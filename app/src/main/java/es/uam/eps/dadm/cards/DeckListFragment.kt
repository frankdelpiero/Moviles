package es.uam.eps.dadm.cards
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.FragmentDeckListBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

class DeckListFragment:Fragment() {
    private lateinit var binding: FragmentDeckListBinding
    private lateinit var adapter: DeckAdapter // Instancio el adaptador
    private var deckIDMax = 1L
    private val executor = Executors.newSingleThreadExecutor()
    private val deckListViewModel  by lazy {
        ViewModelProvider(this).get( DeckListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentDeckListBinding>(
                inflater,
                R.layout.fragment_deck_list,
                container,
                false)
        adapter = DeckAdapter() // Creamos el adaptador
        //adapter.data = CardsApplication.decks
        adapter.data = emptyList()
        deckListViewModel.decks.observe(
                viewLifecycleOwner,
                Observer {
                    Timber.i("SIN ALGO ${it}")
                    adapter.data = it // Ira cambiando cada vez que se recibe una tarjeta
                }
            )
        Timber.i("EEDSDSA ${adapter.data}")
        binding.cardRecyclerViewDeck.adapter = adapter
        /**binding.buttonQuestion.setOnClickListener { view ->
        if (CardsApplication.numberOfCardsLeft()  > 0)
        view.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment2)
        else
        Toast.makeText(activity, R.string.No_cards, Toast.LENGTH_SHORT).show()
        }
        //Escuchador que agrega una nueva carta y accede al panel para editarlo
        binding.newCardFab.setOnClickListener{
            val card = Card("","")
            CardsApplication.addCard(card)
            it.findNavController().navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id))
        }
         */
        binding.newDeckFab.setOnClickListener{

            val deck = Deck(name = "")
            executor.execute {
                deckListViewModel.cardDao.addDeck(deck)
            }
            it.findNavController().navigate(DeckListFragmentDirections.actionDeckListFragment2ToDeckEditFragment(deck.id)) // Navegamos a decKEditFraagment

            //navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id,args.idMazo))

        }
        return binding.root


    }

}