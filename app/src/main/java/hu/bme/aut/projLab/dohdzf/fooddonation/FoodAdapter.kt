

package hu.bme.aut.projLab.dohdzf.fooddonation

import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.content.Intent

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.firestore.FirebaseFirestore

import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ItemLayoutBinding

//import  hu.bme.aut.projLab.dohdzf.fooddonation.Foodadapterbinding

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    var foodlist = ArrayList<Food>()
  var foodKeys = mutableListOf<String>()
   lateinit var currentUid: String
    lateinit var context: Context

  constructor(context: dashboard, uid: String) : super() {
    this.context = context
    this.currentUid = uid
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    val itemView = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    var post = foodlist.get(holder.adapterPosition)
    holder.binding.foodTitle.text = post.titleFood
    holder.binding.userDonor.text=post.userDonor
    holder.binding.itemDescription.text=post.description
    holder.binding.itemLocation.text=post.address


   if(post.imgUrl!!.isNotEmpty() ){
     holder.binding.imageItem.visibility = View.VISIBLE
     Glide.with(context).load(post.imgUrl).into(holder.binding.imageItem)
   }else{
     holder.binding.imageItem.visibility =View.GONE
   }

        holder.binding.cardView.setOnClickListener {
      val intentdetails = Intent (context as dashboard, viewItem::class.java)
          intentdetails.putExtra("KEy", post)
          intentdetails.putExtra("photo", post.imgUrl)
          (context as dashboard).startActivity(intentdetails)
    }

  }

  override fun getItemCount(): Int {
    return foodlist.size
  }

   fun addFood(food:Food, key: String){
     foodlist.add(food)
     foodKeys.add(key)
     //notifyDataSetChanged()
     notifyItemInserted(foodlist.lastIndex)
   }

  fun removeFoodByKey(key: String) {
    val index = foodKeys.indexOf(key)
    if (index != -1) {
      foodlist.removeAt(index)
      foodKeys.removeAt(index)
      notifyItemRemoved(index)
    }
  }




  inner class ViewHolder(val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){}

}
