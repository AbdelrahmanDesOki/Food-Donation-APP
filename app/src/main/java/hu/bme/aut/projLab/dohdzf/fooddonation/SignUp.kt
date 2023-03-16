package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.SignupBinding

class SignUp : AppCompatActivity() {

  private lateinit var binding: SignupBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = SignupBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }

}
