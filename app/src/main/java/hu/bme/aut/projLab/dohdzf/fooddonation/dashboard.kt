package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.bme.aut.projLab.dohdzf.fooddonation.Adapter.FoodAdapter
import hu.bme.aut.projLab.dohdzf.fooddonation.DataClass.Food
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ListViewBinding


class dashboard: AppCompatActivity() {

   private lateinit var  binding: ListViewBinding
   private lateinit var foodArraylist : ArrayList<Food>
   private lateinit var foodAdapter: FoodAdapter
   private lateinit var foodrecyclerview : RecyclerView
   lateinit var context: Context

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ListViewBinding.inflate(layoutInflater)
    setContentView(binding.root)

    context = this
    foodrecyclerview = binding.recyclerPosts
    foodrecyclerview.layoutManager = LinearLayoutManager(this)
    foodrecyclerview.setHasFixedSize(true)


    foodArraylist = arrayListOf<Food>()
    foodAdapter = FoodAdapter(context as dashboard, FirebaseAuth.getInstance().currentUser!!.uid)
    binding.recyclerPosts.adapter = foodAdapter

    //call function to retrieve data from Firebase
     getFoodData()

    binding.add.setOnClickListener{
      Toast.makeText(this@dashboard,"Please Fill Sections by order ⬇️", Toast.LENGTH_LONG).show()
      val intentdetails = Intent (this, add_food::class.java)
      startActivity(intentdetails)
    }
  }


private fun getFoodData() {
  //retrieving data from Database
  val db = FirebaseFirestore.getInstance().collection("Users")
  db.addSnapshotListener(
    object: EventListener<QuerySnapshot>{

      override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error !=null){
          Toast.makeText(this@dashboard,"error: ${error.message} ", Toast.LENGTH_LONG).show()
          return
        }
        for (docChange in value?.getDocumentChanges()!!) {
          if (docChange.type == DocumentChange.Type.ADDED) {
            val post = docChange.document.toObject(Food::class.java)
            foodAdapter.addFood(post, docChange.document.id)
          } else if (docChange.type == DocumentChange.Type.REMOVED) {
            foodAdapter.removeFoodByKey(docChange.document.id)
          } else if (docChange.type == DocumentChange.Type.MODIFIED) {

          }
        }
      }

    }
  )

}



}
