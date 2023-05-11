package hu.bme.aut.projLab.dohdzf.fooddonation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.SignupBinding
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ViewFoodBinding

class viewItem: AppCompatActivity() {

  private lateinit var binding: ViewFoodBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ViewFoodBinding.inflate(layoutInflater)
    setContentView(binding.root)
    //Intent put extra
    //    intent.extras
    var food =  intent.getSerializableExtra("KEy") as Food
    binding.title.text = food.titleFood


  }
}
