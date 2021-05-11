package es.uam.eps.dadm.cardsFranccySambrano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cardsFranccySambrano.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.setDefaultValues(
            this,
            R.xml.root_preferences,
            false
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference = database.getReference("mensaje")
        reference.setValue("Hola desde Cards")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                Toast.makeText(
                    baseContext,
                    "${p0.value.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        NavigationUI.setupWithNavController(
                binding.navView,
                findNavController(R.id.navHostFragment))
        //supportActionBar?.hide()
        var fragment = supportFragmentManager //Lista de fragmentos
                .findFragmentById(R.id.navHostFragment)

        if (fragment == null){ //En caso de que sea la primera vez que registramos el fragmento
            fragment = TitleFragment()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.navHostFragment, fragment)
                    .commit()
        }
    }
    private fun getUserProfile() {
        // [START get_user_profile]
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
        // [END get_user_profile]
    }
}
