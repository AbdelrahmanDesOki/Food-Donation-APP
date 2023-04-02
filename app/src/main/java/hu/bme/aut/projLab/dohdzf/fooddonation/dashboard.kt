package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ListViewBinding


class dashboard: AppCompatActivity() {

  private lateinit var binding: ListViewBinding
//  lateinit var add: FloatingActionButton


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ListViewBinding.inflate(layoutInflater)
    setContentView(binding.root)


    binding.add.setOnClickListener{
      val intentdetails = Intent (this, add_food::class.java)
      startActivity(intentdetails)
    }
  }





}
