package es.uam.eps.dadm.cards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.ActivityStudyBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false)

        binding.cardsTitleTextView.setOnClickListener { view ->
            Timber.i("Left cards ")
            if (CardsApplication.numberOfCardsLeft()  > 0)
                view.findNavController().navigate(R.id.action_titleFragment_to_cardListFragment)
            else
                Toast.makeText(activity, R.string.No_cards, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}



