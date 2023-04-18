package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

//  var  KEY_User = "KEY_NAME"
//  var KEY_Password = "KEY_EMAIL"

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)


      //trial here
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.signup.setOnClickListener{

        val intentdetails = Intent (this, add_food::class.java)
        startActivity(intentdetails)

      }
    }
}
