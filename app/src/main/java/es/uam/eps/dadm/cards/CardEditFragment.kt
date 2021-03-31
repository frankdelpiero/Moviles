package es.uam.eps.dadm.cards

import android.graphics.drawable.AnimatedStateListDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentCardEditBinding
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var card:Card
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
    lateinit var question:String
    lateinit var answer:String

    private lateinit var binding:FragmentCardEditBinding

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
        binding.acceptButton.setOnClickListener{
            card.question = question
            card.answer = answer
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment())

        }

        binding.cancelButton.setOnClickListener{
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment())
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
        card = CardsApplication.getCard(args.cardId)
        binding.card = card
        question = card.question
        answer = card.answer
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