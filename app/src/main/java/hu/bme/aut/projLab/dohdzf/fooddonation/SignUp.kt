package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.SignupBinding

class SignUp : AppCompatActivity() {

  private lateinit var binding: SignupBinding

  companion object {
    var  KEY_Name = "KEY_NAME"
    var KEY_Email = "KEY_EMAIL"
    var KEY_Password = "KEY_PASSWORD"
    var KEY_Pass_Validation = "KEY_PASS_VALIDATION"
  }



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = SignupBinding.inflate(layoutInflater)
    setContentView(binding.root)


    binding.signup.setOnClickListener{

      var intentDetails = Intent()

      KEY_Name = binding.loginName.text.toString()
      KEY_Email = binding.embed.text.toString()
      KEY_Password = binding.loginPassword.text.toString()
      KEY_Pass_Validation = binding.confirmButton.text.toString()

      if(KEY_Name.isNullOrEmpty() ) {
        binding.loginName.setError("CAnnot be empty")
      }
      else if (KEY_Email.isNullOrEmpty()) {
        binding.embed.setError("CAnnot be empty")
      }
      else if(KEY_Password.isNullOrEmpty()) {
        binding.loginPassword.setError("CAnnot be empty")
      }
      else if (KEY_Pass_Validation.isNullOrEmpty() || !KEY_Pass_Validation.equals(KEY_Password)){
        binding.confirmButton.setError("CAnnot be empty")
      }
      else{

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(KEY_Email, KEY_Password).addOnCompleteListener{
            task ->
          if (task.isSuccessful) {
            Toast.makeText(this, "Registration OK", Toast.LENGTH_LONG).show()
//            val user = task.result?.user
          } else {
            Toast.makeText(this, "Failed to Register Please try again", Toast.LENGTH_LONG).show()
//            val exception = task.exception
          }
        }


       intentDetails.setClass(this, MainActivity::class.java)
        startActivity(intentDetails)
      }

    }


  }



}
