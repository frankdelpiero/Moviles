package es.uam.eps.dadm.cardsFranccySambrano
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cardsFranccySambrano.database.CardDatabase
import es.uam.eps.dadm.cardsFranccySambrano.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cardsFranccySambrano.databinding.FragmentDeckListBinding
import es.uam.eps.dadm.cardsFranccySambrano.databinding.FragmentTitleBinding
import timber.log.Timber
import java.util.concurrent.Executors
private const val DATABASENAME = "tarjetas"
class DeckListFragment:Fragment() {
    private lateinit var binding: FragmentDeckListBinding
    private lateinit var adapter: DeckAdapter // Instancio el adaptador
    private lateinit var userSession: Session
    private lateinit var intentt: Intent
    private var deckIDMax = 1L
    // Añadir tarjetas a Firebase
    private var reference = FirebaseDatabase
            .getInstance()
            .getReference(DATABASENAME)
    private val executor = Executors.newSingleThreadExecutor()
    private val deckListViewModel  by lazy {
        ViewModelProvider(this).get( DeckListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentt = Intent(context, EmailPasswordActivity::class.java)

        userSession = Session(Firebase.auth.currentUser.email,"")
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
                    adapter.data = it // Ira cambiando cada vez que se recibe una tarjeta
                }
            )
        binding.cardRecyclerViewDeck.adapter = adapter
        deckListViewModel.cardsToUpdater.observe( viewLifecycleOwner,Observer {
          binding.invalidateAll()
        })



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

            val deck = Deck(name = "",userID = Firebase.auth.currentUser.uid)
            executor.execute {
                deckListViewModel.cardDao.addDeck(deck)
            }
            it.findNavController().navigate(DeckListFragmentDirections.actionDeckListFragment2ToDeckEditFragment(deck.id)) // Navegamos a decKEditFraagment

            //navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id,args.idMazo))
        }

        binding.deleteDeckFab.setOnClickListener{
            it.findNavController().navigate(DeckListFragmentDirections.actionDeckListFragment2ToDeckDeleteFragment())
        }

        binding.signOutFab.setOnClickListener{
            Timber.i("Desconectando a ${userSession.username}")
            FirebaseAuth.getInstance().signOut()
            startActivity(intentt)
        }

        binding.updateFab.setOnClickListener{
            Timber.i("Subiendo cartas...")
            for (c in deckListViewModel.cardsToUpdater.value!!){
                Timber.i("CARTA ${c.question} y ${c.answer}")
                reference.child(c.id).setValue(c)
            }
        }

        return binding.root


    }

}