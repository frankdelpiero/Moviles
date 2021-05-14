package es.uam.eps.dadm.cardsFranccySambrano

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cardsFranccySambrano.databinding.EmailPasswordBinding
import timber.log.Timber

class EmailPasswordActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : EmailPasswordBinding
     var username = " "
    var password = " "
    private lateinit var session:Session
    private lateinit var intentt:Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentt = Intent(this, TitleActivity::class.java)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.email_password)
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }

        val userTextWatcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                username = s.toString()
            }

        }

        val passwordTextWatcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = s.toString()
            }

        }


        binding.user.addTextChangedListener(userTextWatcher)
        binding.password.addTextChangedListener(passwordTextWatcher)

        binding.registerButton.setOnClickListener{
            session = Session(username,password)

            createAccount(session.username,session.password)
        }

        binding.InitButton.setOnClickListener{
            if (password == null){ //
                Timber.i("Esta entrando en pass")
                password = " "
            }
            if (username == null){
                Timber.i("Esta entrando en user")
                username = " "
            }
            session = Session(username,password)
            signIn(session.username,session.password)
        }

    }

    // Creacion de usuarios nuevos
    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        Timber.i("email: $email")
        Timber.i("password $password")
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.i( "createUserWithEmail:success")
                        val user = auth.currentUser
                        //updateUI(user)
                        startActivity(intentt)
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.i( "createUserWithEmail:failure")
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        // [END create_user_with_email]
    }

    //Acceso a usuarios que ya existen
    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.i( "signeUserWithEmail:success")
                        val user = auth.currentUser
                       // updateUI(user)
                        //startActivity(intent)
                        startActivity(intentt)
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.i( "signUserWithEmail:failure")
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        // [END sign_in_with_email]
    }


    fun reload(){

    }

}