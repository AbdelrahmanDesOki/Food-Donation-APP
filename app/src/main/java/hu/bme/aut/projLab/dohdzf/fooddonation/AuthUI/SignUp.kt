package hu.bme.aut.projLab.dohdzf.fooddonation.AuthUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.projLab.dohdzf.fooddonation.MainActivity
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
      //storing data in order to be able to verify it's correctness
      KEY_Name = binding.loginName.text.toString()
      KEY_Email = binding.embed.text.toString()
      KEY_Password = binding.loginPassword.text.toString()
      KEY_Pass_Validation = binding.confirmButton.text.toString()

      if(KEY_Name.isNullOrEmpty() ) {
        binding.loginName.setError("Cannot be empty")
      }
      else if (KEY_Email.isNullOrEmpty()) {
        binding.embed.setError("Cannot be empty")
      }
      else if(KEY_Password.isNullOrEmpty()) {
        binding.loginPassword.setError("Cannot be empty")
      }
      else if (KEY_Pass_Validation.isNullOrEmpty()  ){
        binding.confirmButton.setError("Cannot be empty")
      }
      else if( !KEY_Pass_Validation.equals(KEY_Password) ){
        binding.confirmButton.setError("Make sure confirmation similar to normal")
      }
      else{
        //sending data to Firebase if it passed all the tests
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(KEY_Email, KEY_Password).addOnCompleteListener{
            task ->
          if (task.isSuccessful) {
            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show()

          } else {
            Toast.makeText(this, "Failed to Register Please try again", Toast.LENGTH_LONG).show()
          }
        }

       intentDetails.setClass(this, MainActivity::class.java)
        startActivity(intentDetails)
      }

    }


  }



}
