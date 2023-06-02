package hu.bme.aut.projLab.dohdzf.fooddonation


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.projLab.dohdzf.fooddonation.DataClass.Food
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ViewFoodBinding
import kotlin.properties.Delegates

class viewItem: AppCompatActivity() {

  private lateinit var binding: ViewFoodBinding
  lateinit var context: Context


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ViewFoodBinding.inflate(layoutInflater)
    setContentView(binding.root)

    //getting data from add-food layout
    var food =  intent.getSerializableExtra("KEy") as Food
    var photo = intent.getStringExtra("photo")

    binding.title.text = "Title: " + food.titleFood
    binding.name.text =  "Name of Donor: " + food.userDonor
    binding.description.text= "Description: " + food.description
    binding.contact.text=  food.email
    binding.address.text= "Location: " + food.address
    binding.availability.text = "Available till: " + food.availableItems


    binding.image.visibility= View.VISIBLE
    Glide.with(this ).load(photo).into(binding.image)


    //sending mail to user to request item based on email address.
    binding.request.setOnClickListener {

      FirebaseAuth.getInstance().sendPasswordResetEmail(binding.contact.text.toString()).addOnCompleteListener{
        task ->
        if(task.isSuccessful){
          Toast.makeText(this, "Mail is sent Successfully ☑️", Toast.LENGTH_SHORT).show()

          Toast.makeText(this, "Item reserved for you Successfully ☑️", Toast.LENGTH_SHORT).show()

        }else{
          Toast.makeText(this, "Failed to request this Item", Toast.LENGTH_SHORT).show()

        }
        val intent = Intent(this, dashboard::class.java)
        startActivity(intent)
        finish()
      }
    }
  }


}
