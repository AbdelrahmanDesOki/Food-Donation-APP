package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ListViewBinding


class dashboard: AppCompatActivity() {

   private lateinit var  binding: ListViewBinding
   private lateinit var dbref : DatabaseReference
   private lateinit var foodArraylist : ArrayList<Food>

  private lateinit var foodAdapter: FoodAdapter
   private lateinit var foodrecyclerview : RecyclerView


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ListViewBinding.inflate(layoutInflater)
    setContentView(binding.root)


    foodrecyclerview = binding.recyclerPosts
    foodrecyclerview.layoutManager = LinearLayoutManager(this)
    foodrecyclerview.setHasFixedSize(true)


    foodArraylist = arrayListOf<Food>()



    binding.recyclerPosts.adapter = foodAdapter
//    _binding.recyclerPosts.adapter = foodAdapter
     getFoodData()




    binding.add.setOnClickListener{
      val intentdetails = Intent (this, add_food::class.java)
      startActivity(intentdetails)
    }
  }


private fun getFoodData() {


    var foodRecyclerView : RecyclerView ?= null


  dbref = FirebaseDatabase.getInstance().getReference("Users")
  dbref.addValueEventListener(object : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {

      if(snapshot.exists()){
        for(foodSnapShot in snapshot.children){
          val food = foodSnapShot.getValue(Food::class.java)
          //all data fetched in this arraylist
          foodArraylist.add(food!!)
        }
        foodRecyclerView?.adapter = FoodAdapter()
      }

    }

    override fun onCancelled(error: DatabaseError) {
      TODO("Not yet implemented")
    }


  })
}



}
