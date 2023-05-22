package hu.bme.aut.projLab.dohdzf.fooddonation


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
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

    var food =  intent.getSerializableExtra("KEy") as Food
    var photo = intent.getStringExtra("photo")

    binding.title.text = food.titleFood
    binding.name.text = food.userDonor
    binding.description.text=food.description
    binding.contact.text=food.email
    binding.address.text=food.address


    binding.image.visibility= View.VISIBLE
    Glide.with(this ).load(photo).into(binding.image)







//need to fix email format
    binding.request.setOnClickListener {
      FirebaseAuth.getInstance().sendPasswordResetEmail(binding.contact.text.toString()).addOnCompleteListener{
        task ->
        if(task.isSuccessful){
          Toast.makeText(this, "MAil Sent successfully ☑️", Toast.LENGTH_SHORT).show()
        }else{
          Toast.makeText(this, "FAiled to send the emaill️", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent(this, dashboard::class.java)
        startActivity(intent)
        finish()
      }
    }
  }
}
