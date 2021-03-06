package es.uam.eps.dadm.cardsFranccySambrano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.cardsFranccySambrano.databinding.ActivityStudyBinding
import es.uam.eps.dadm.cardsFranccySambrano.databinding.FragmentStudyBinding
import es.uam.eps.dadm.cardsFranccySambrano.databinding.FragmentTitleBinding
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val DATABASENAME = "tarjetas"
/**
 * A simple [Fragment] subclass.
 * Use the [StudyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var reference = FirebaseDatabase
            .getInstance()
            .getReference(DATABASENAME)
    private val viewModel: StudyViewModel by lazy { //Instancia de view model
        ViewModelProvider(this).get(StudyViewModel::class.java)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*    val observer = object: Observer<Int> {
                override fun onChanged(t: Int?) {
                    binding.infoTextView?.text = t.toString()
                }
            }

         */
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentStudyBinding>(
                inflater,
                R.layout.fragment_study,
                container,
                false)
        binding.studyViewModel = viewModel // Va a usar view model
        binding.lifecycleOwner = this
        val showBoard = context?.let {  SettingsActivity.getValueBoard(it) }
        if (showBoard == true){
            binding.board.visibility = View.VISIBLE
        } else{
            binding.board.visibility = View.INVISIBLE
        }
        val listener = View.OnClickListener { v ->
            // Asigna a quality el valor 0, 3 o 5,
            // dependiendo del bot??n que se haya pulsado
            val quality = when (v?.id) {
                R.id.easyButton -> 5
                R.id.doubtButton -> 3
                R.id.difficultButton -> 0
                else -> throw Exception("Unavailable quality")
            }
            Timber.i("quality $quality")
            // Llama al m??todo update de viewModel
            viewModel.update(quality)
            // Si la propiedad card de viewModel es null
            // informa al usuario mediante un Toast de que
            // no quedan tarjetas
            reference.child(viewModel.card!!.id).setValue(viewModel.card) // A??ade la carta a Firebase
            if (viewModel.card == null){
                Toast.makeText(v.context,R.string.No_cards, Toast.LENGTH_LONG).show()
            }
            binding.invalidateAll()
        }

        // Observador de duecard
        viewModel.dueCard.observe(viewLifecycleOwner,Observer{
            viewModel.card = it
            binding.invalidateAll()
        })



        // Incluyo la interaccion con cardsLeft
        //viewModel.cardsLeft.observe(this, observer)
        binding.apply {
            answerButton.setOnClickListener {
                viewModel.card?.answered = true
                binding.invalidateAll()
            }
            binding.easyButton.setOnClickListener(listener)
            binding.doubtButton.setOnClickListener(listener)
            binding.difficultButton.setOnClickListener(listener)
            //startActivity(Intent(activity, StudyActivity::class.java))
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StudyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                StudyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}