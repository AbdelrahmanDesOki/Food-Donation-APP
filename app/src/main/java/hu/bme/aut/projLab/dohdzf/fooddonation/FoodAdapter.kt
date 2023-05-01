package hu.bme.aut.projLab.dohdzf.fooddonation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class FoodAdapter(private val foodlist: ArrayList<Food> ) : RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {



  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)


    return MyViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    val currentitem = foodlist[position]

    //might not work properly
//    holder.imageFood.setImageResource(currentitem.imageFood!!)

    holder.titleFood.text = currentitem.titleFood
    holder.Userdonor.text = currentitem.userDonor


  }

  override fun getItemCount(): Int {
    return foodlist.size
  }




  class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageFood : ImageView = itemView.findViewById(R.id.imageItem)
        val titleFood : TextView = itemView.findViewById(R.id.food_title)
        val Userdonor : TextView = itemView.findViewById(R.id.userDonor)
  }



}

private fun ImageView.setImageResource(imageFood: ImageView) {

}
