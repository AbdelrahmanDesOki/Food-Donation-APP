

package hu.bme.aut.projLab.dohdzf.fooddonation

import android.view.LayoutInflater
import android.view.View
import android.content.Context

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ItemLayoutBinding

//import  hu.bme.aut.projLab.dohdzf.fooddonation.Foodadapterbinding

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

   private val foodlist = ArrayList<Food>()
   lateinit var currentUid: String
    lateinit var context: Context

//  constructor(context: Context, uid: String) : super() {
//    this.context = context
//    this.currentUid = uid
//  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    val itemView = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)


    return ViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {




    var post = foodlist.get(holder.adapterPosition)
    holder.binding.foodTitle.text = post.titleFood
    holder.binding.userDonor.text=post.userDonor

   if(post.imgUrl?.isNotEmpty() == true){
     holder.binding.imageItem.visibility = View.VISIBLE
     Glide.with(context).load(post.imgUrl).into(holder.binding.imageItem)
   }else{
     holder.binding.imageItem.visibility =View.GONE
   }







//    val currentitem = foodlist[position]

    //might not work properly
//    holder.imageFood.setImageResource(currentitem.imageFood!!)

//    holder.titleFood.text = currentitem.titleFood
//    holder.Userdonor.text = currentitem.userDonor


  }

  override fun getItemCount(): Int {
    return foodlist.size
  }


    fun updateFoodlist(foodlist: List<Food >){

      this.foodlist.clear()
      this.foodlist.addAll(foodlist)
      notifyDataSetChanged()

    }





//  inner class ViewHolder(val binding: Foodadapterbinding) : RecyclerView.ViewHolder(binding.root){
//  }


  inner class ViewHolder(val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){}


}

private fun ImageView.setImageResource(imageFood: ImageView) {


}
