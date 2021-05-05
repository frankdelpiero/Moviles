package es.uam.eps.dadm.cardsFranccySambrano

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import es.uam.eps.dadm.cardsFranccySambrano.databinding.FragmentCardDeleteBinding
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//private lateinit var card:Card
/**
 * A simple [Fragment] subclass.
 * Use the [CardEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardDeleteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var deck: Deck
    var deckId = 1L
    // lateinit var question:String
    // lateinit var answer:String
    lateinit var name:String
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var binding: FragmentCardDeleteBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(CardDeleteViewModel::class.java)
    }
    override fun onStart() {
        super.onStart()
        val deckNameTextWatcher = object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()
            }

        }

        binding.cardNameDelete.addTextChangedListener(deckNameTextWatcher)
        //
        binding.acceptCardDeleteButton.setOnClickListener{
            // deck.name = name
            executor.execute{
                //context?.let { CardDatabase.getInstance(it).cardDao.update(card) }
                //Actualiza el mazo
                //viewModel.getContext.deleteCardsOfDeck(name)
                //viewModel.getContext.deleteDeckByName(name)
                viewModel.getContext.deleteCardOfDeck(name,deckId)
            }
            it.findNavController().navigate(CardDeleteFragmentDirections.actionCardDeleteFragmentToCardListFragment3(deckId))
            //it.findNavController().navigate(CardDeleteFragmen)
            //.navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment(deckId))

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentCardDeleteBinding>(
                inflater,
                R.layout.fragment_card_delete,
                container,
                false)
        val args = CardDeleteFragmentArgs.fromBundle(requireArguments())
        deckId = args.idMazo
        //viewModel.loadCardId(args.cardId)
        //viewModel.loadCardDeck(args.idMazo)
        //viewModel.deck.observe(viewLifecycleOwner) {
        //    deck = it
        //    binding.deck= deck   // card = card
        // question = card.question
        // answer = card.answer
        //    name = deck.name
        //}
        /**
        card = CardsApplication.getCard(cardId,deckId)
        binding.card = card
        question = card.question
        answer = card.answer
         */

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CardEditFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}

