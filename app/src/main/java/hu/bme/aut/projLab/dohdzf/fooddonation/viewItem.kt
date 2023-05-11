package hu.bme.aut.projLab.dohdzf.fooddonation


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.SignupBinding
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ViewFoodBinding

class viewItem: AppCompatActivity() {

  private lateinit var binding: ViewFoodBinding
  lateinit var context: Context

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ViewFoodBinding.inflate(layoutInflater)
    setContentView(binding.root)
    //Intent put extra
    //    intent.extras
    var food =  intent.getSerializableExtra("KEy") as Food
    binding.title.text = food.titleFood
    binding.name.text = food.userDonor
    binding.description.text=food.description
//    this.context = viewItem
//    Glide.with(context as viewItem).load(food.imgUrl).into(binding.image)

  }
}
