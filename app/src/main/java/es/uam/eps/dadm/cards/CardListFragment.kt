package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

class CardListFragment: Fragment(){
    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardAdapter // Instancio el adaptador
    private val executor = Executors.newSingleThreadExecutor()

    // Infla el menu del fichero
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Pasa el identificador del fichero
        inflater.inflate(R.menu.fragment_card_list, menu)
    }
    // Responde a las pulsaciones de los items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                // Aquí irá el código para arrancar las preferencias de Cards

            }
        }
        return true
    }

    // Añado un nuevo viewmodel instanciandolo
    private val cardListViewModel  by lazy {
        ViewModelProvider(this).get( CardListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)  // Para hacer aparecer la barra
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
        //
        var args = CardListFragmentArgs.fromBundle(requireArguments())
        Timber.i("EL ID DEL MAZO PASADO ES ${args.idMazo}")
        var deck =  cardListViewModel.getContext.getDeck(args.idMazo)
        //
        Timber.i("EL DECK QUE CONSIGO ES ${deck}")
        adapter = CardAdapter() // Creamos el adaptador
        // adapter.data = deck.cards // Lista de cartas de un almacen en especifico
        adapter.data = emptyList()
        adapter.dataDeck = mutableListOf(args.idMazo)
        Timber.i("RECIBE ${adapter.dataDeck}")
       // adapter.dataDeck = mutableListOf(args.idMazo)
        //adapter.dataDeck = mutableListOf(args.idMazo) //ID de la carta
         binding.cardRecyclerView.adapter = adapter // Adaptador de las cartas

         /**binding.buttonQuestion.setOnClickListener { view ->
             if (CardsApplication.numberOfCardsLeft()  > 0)
                 view.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment2)
             else
                 Toast.makeText(activity, R.string.No_cards, Toast.LENGTH_SHORT).show()
         }*/
        //Escuchador que agrega una nueva carta y accede al panel para editarlo
        binding.newCardFab.setOnClickListener{
            val card = Card("","",args.idMazo)
            //CardsApplication.addCard(card,deck.id) // Añado la carta a un ID en especifico
            //CardsApplication.addCard(card)
            executor.execute{
                cardListViewModel.getContext.addCard(card)
                // context?.let { CardDatabase.getInstance(it).cardDao.addCard(card) }
            }
            it.findNavController().navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id,args.idMazo))
        }
        binding.deleteCardFab?.setOnClickListener{
            it.findNavController().navigate(CardListFragmentDirections.actionCardListFragmentToCardDeleteFragment2(args.idMazo))
        }
       cardListViewModel.getContext.getTypeDeckCard(args.idMazo).observe(
           viewLifecycleOwner,
           Observer {
               adapter.data = it // Ira cambiando cada vez que se recibe una tarjeta
               adapter.notifyDataSetChanged()
           }
       )
        // OBTENGO TODAS LAS CARTAS
       /** cardListViewModel.cards.observe(
            viewLifecycleOwner,
            Observer {
                Timber.i("QUE TE APUESTAS")
                adapter.data = it // Ira cambiando cada vez que se recibe una tarjeta
                adapter.notifyDataSetChanged()
            }
        )*/
        return binding.root


    }

}