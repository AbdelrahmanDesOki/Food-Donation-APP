package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ListViewBinding


class dashboard: AppCompatActivity() {

  private lateinit var binding: ListViewBinding

  private lateinit var dbref : DatabaseReference
  private lateinit var foodRecyclerView : RecyclerView
  private lateinit var foodArraylist : ArrayList<Food>




  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ListViewBinding.inflate(layoutInflater)
    setContentView(binding.root)

    foodRecyclerView = findViewById(R.id.foodList)
    foodRecyclerView.layoutManager = LinearLayoutManager(this)
    foodRecyclerView.setHasFixedSize(true)


    foodArraylist = arrayListOf<Food>()
    getFoodData()





    binding.add.setOnClickListener{
      val intentdetails = Intent (this, add_food::class.java)
      startActivity(intentdetails)
    }
  }

  private fun getFoodData() {

    dbref = FirebaseDatabase.getInstance().getReference("Foods")
    dbref.addValueEventListener(object : ValueEventListener{
      override fun onDataChange(snapshot: DataSnapshot) {

        if(snapshot.exists()){
                 for(foodSnapShot in snapshot.children){
                              val food = foodSnapShot.getValue(Food::class.java)
                         //all data fetched in this arraylist
                               foodArraylist.add(food!!)
                 }
          foodRecyclerView.adapter = FoodAdapter(foodArraylist)
        }

      }

      override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
      }


    })
  }


}
