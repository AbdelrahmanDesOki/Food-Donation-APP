package hu.bme.aut.projLab.dohdzf.fooddonation

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.AddFoodBinding


class add_food:AppCompatActivity() {

private lateinit var binding: AddFoodBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = AddFoodBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }


}
