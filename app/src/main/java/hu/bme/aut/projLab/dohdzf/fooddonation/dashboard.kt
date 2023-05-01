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

  lateinit var imageId : Array<Int>
  lateinit var title : Array<String>
  lateinit var loc : Array<String>


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ListViewBinding.inflate(layoutInflater)
    setContentView(binding.root)


    imageId = arrayOf(
      R.drawable.cake,
      R.drawable.choco,
      R.drawable.choco_cake,
      R.drawable.fuztea,
      R.drawable.kiwi,
      R.drawable.macrony
    )
    title = arrayOf(
      "CAke for sell",
      "CAke for sell",
      "CAke for sell",
      "CAke for sell",
      "CAke for sell",
      "CAke for sell"
      )
    loc = arrayOf(
      "Buda, Hun",
      "Buda, Hun",
      "Buda, Hun",
      "Buda, Hun",
      "Buda, Hun",
      "Buda, Hun"
    )


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

//    for(i in imageId.indices){
//        val Foods = Food(imageId[i], title[i], loc[i])
//        foodArraylist.add(Foods)
//    }
//    foodRecyclerView.adapter = FoodAdapter(foodArraylist)
//








//    dbref = FirebaseDatabase.getInstance().getReference("Foods")
//    dbref.addValueEventListener(object : ValueEventListener{
//      override fun onDataChange(snapshot: DataSnapshot) {
//
//        if(snapshot.exists()){
//                 for(foodSnapShot in snapshot.children){
//                              val food = foodSnapShot.getValue(Food::class.java)
//                         //all data fetched in this arraylist
//                               foodArraylist.add(food!!)
//                 }
//          foodRecyclerView.adapter = FoodAdapter(foodArraylist)
//        }
//
//      }
//
//      override fun onCancelled(error: DatabaseError) {
//        TODO("Not yet implemented")
//      }
//
//
//    })
  }


}
