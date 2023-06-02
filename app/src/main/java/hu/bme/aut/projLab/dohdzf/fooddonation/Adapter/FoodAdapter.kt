package hu.bme.aut.projLab.dohdzf.fooddonation.Adapter

import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.projLab.dohdzf.fooddonation.DataClass.Food
import hu.bme.aut.projLab.dohdzf.fooddonation.dashboard
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ItemLayoutBinding
import hu.bme.aut.projLab.dohdzf.fooddonation.viewItem


class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    var foodlist = ArrayList<Food>()
    var foodKeys = mutableListOf<String>()
    var currentUid: String
    var context: Context

  constructor(context: dashboard, uid: String) : super() {
    this.context = context
    this.currentUid = uid
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    val itemView = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    //binding the items in the card layout with data class
    var post = foodlist.get(holder.adapterPosition)
    holder.binding.foodTitle.text = post.titleFood
    holder.binding.userDonor.text=post.userDonor
    holder.binding.itemDescription.text=post.description
    holder.binding.itemLocation.text=post.address
    holder.binding.available.text=post.availableItems

    //binding photo with Glide open-source library
     if(post.imgUrl!!.isNotEmpty() ){
       holder.binding.imageItem.visibility = View.VISIBLE
       Glide.with(context).load(post.imgUrl).into(holder.binding.imageItem)
     }else{
       holder.binding.imageItem.visibility =View.GONE
     }

        holder.binding.cardView.setOnClickListener {
      val intentdetails = Intent (context as dashboard, viewItem::class.java)
          //Keys used to send data between layouts
          intentdetails.putExtra("KEy", post)
          intentdetails.putExtra("photo", post.imgUrl)
          (context as dashboard).startActivity(intentdetails)
    }

  }

  override fun getItemCount(): Int {
    return foodlist.size
  }

   fun addFood(food: Food, key: String){
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
