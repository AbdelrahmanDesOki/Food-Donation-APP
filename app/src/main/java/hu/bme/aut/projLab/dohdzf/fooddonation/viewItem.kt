package hu.bme.aut.projLab.dohdzf.fooddonation


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
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
    binding.contact.text=food.email
//    this.context = viewItem
//    Glide.with(context as viewItem).load(food.imgUrl).into(binding.image)







//need to fix email format
    binding.request.setOnClickListener {
      FirebaseAuth.getInstance().sendPasswordResetEmail(binding.contact.text.toString()).addOnCompleteListener{
        task ->
        if(task.isSuccessful){
          Toast.makeText(this, "MAil Sent successfully ☑️", Toast.LENGTH_SHORT).show()
        }else{
          Toast.makeText(this, "FAiled to send the emaill️", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}
