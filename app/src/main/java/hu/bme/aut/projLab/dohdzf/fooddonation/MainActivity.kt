package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ActivityMainBinding
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.projLab.dohdzf.fooddonation.AuthUI.SignUp

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private var  KEY_LOG = "KEY_LOGIN"
  private var KEY_Pass = "KEY_PASS"


    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.signup.setOnClickListener{
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
        finish()
      }

      binding.login.setOnClickListener{
        KEY_LOG = binding.loginName.text.toString()
        KEY_Pass = binding.loginPassword.text.toString()

        if(KEY_LOG.isNullOrEmpty() ) {
          binding.loginName.setError("Cannot be empty")
        }
        else if (KEY_Pass.isNullOrEmpty()) {
          binding.loginPassword.setError("Cannot be empty")
        }
        else{
          //authenticate user data with data stored in Firebase Auth
          FirebaseAuth.getInstance().signInWithEmailAndPassword(KEY_LOG, KEY_Pass).addOnCompleteListener(this){
              task -> if (task.isSuccessful) {
            // Authentication successful, proceed to next activity
            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)
            Toast.makeText(this, "Successfully Authenticated ☑️", Toast.LENGTH_SHORT).show()
            finish()
          } else {
            // Authentication failed, display error message
            Toast.makeText(this, "Authentication failed ❌", Toast.LENGTH_SHORT).show()
          }
          }
        }
      }
    }
}
