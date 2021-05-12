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
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.cardsFranccySambrano.databinding.FragmentCardEditBinding
import es.uam.eps.dadm.cardsFranccySambrano.databinding.FragmentCardListBinding
import timber.log.Timber
import java.util.concurrent.Executors
private const val DATABASENAME = "tarjetas"
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
class CardEditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var card:Card
    private lateinit var cardId:String
    var deckId = 1L
    lateinit var question:String
    lateinit var answer:String
    private val executor = Executors.newSingleThreadExecutor()
    // Añadir tarjetas a Firebase
    private var reference = FirebaseDatabase
            .getInstance()
            .getReference(DATABASENAME)
    private lateinit var binding:FragmentCardEditBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(CardEditViewModel::class.java)
    }
    override fun onStart() {
        super.onStart()
        val questionTextWatcher = object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                question = s.toString()
            }

        }

        val answerTextWatcher = object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                answer = s.toString()
            }
        }
        binding.questionText.addTextChangedListener(questionTextWatcher) // Cambio el nombre de la pregunta
        binding.answerText.addTextChangedListener(answerTextWatcher) // Cambiamos la respuesta
        binding.acceptCardEditButton.setOnClickListener{
            card.question = question
            card.answer = answer
            executor.execute{
                //context?.let { CardDatabase.getInstance(it).cardDao.update(card) }
                //Actualiza la tarjeta
                viewModel.getContext.update(card)
            }
            reference.child(card.id).setValue(card) // Añade la carta a Firebase
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment(deckId))

        }

        binding.cancelCardEditButton.setOnClickListener{
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment(deckId))
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
        binding = DataBindingUtil.inflate<FragmentCardEditBinding>(
            inflater,
            R.layout.fragment_card_edit,
            container,
            false)
        val args = CardEditFragmentArgs.fromBundle(requireArguments())
        cardId = args.cardId
        deckId = args.idMazo
        viewModel.loadCardId(args.cardId)
        viewModel.card.observe(viewLifecycleOwner) {
            card = it
            binding.card = card
            question = card.question
            answer = card.answer
        }
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