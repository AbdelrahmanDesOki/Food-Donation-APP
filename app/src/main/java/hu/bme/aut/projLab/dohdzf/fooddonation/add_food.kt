package hu.bme.aut.projLab.dohdzf.fooddonation

import android.Manifest
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.AddFoodBinding
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.*
//import kotlinx.android.synthetic.main.add_food.*


class add_food:AppCompatActivity() {

    private lateinit var binding: AddFoodBinding
//    private lateinit var bindingItem: ItemLayoutBinding
    private lateinit var auth: FirebaseAuth
    var pickedPhoto: Uri? = null
    var pickedBitmap: Bitmap ? = null
    var foodView: ImageView? = null
   private lateinit var storageReference: StorageReference
   private lateinit var imageUri: Uri

  private lateinit var db : DatabaseReference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = AddFoodBinding.inflate(layoutInflater)
    setContentView(binding.root)
    //validation with User id to store data
    auth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid
    db = FirebaseDatabase.getInstance().getReference("Users")


//     foodView  = binding.imageFood

    binding.map.setOnClickListener{
      val intent = Intent(this, MapsActivity::class.java)
      startActivity(intent)
      finish()
    }

    binding.add.setOnClickListener {

            val titleFood = binding.foodTitle.text.toString()
            val userDonor = binding.name.text.toString()

             val food = Food(titleFood, userDonor)

      if(uid != null){
        db.child(uid).setValue(food).addOnCompleteListener{
          if(it.isSuccessful){
            Toast.makeText(this@add_food,"Data Uploaded Successfully ", Toast.LENGTH_LONG).show()
            uploadImage()

          }else{
            Toast.makeText(this@add_food,"FAiled to store data ", Toast.LENGTH_LONG).show()
          }
        }
      }








//      val progressDialog = ProgressDialog(this)
//      progressDialog.setMessage("Uploading your data..")
//      progressDialog.setCancelable(false)
//      progressDialog.show()
//
//      val formatter = SimpleDateFormat("yyyy_MM_DD_HH_mm_ss", Locale.getDefault())
//      val now = Date()
//      val fileName = formatter.format(now)
//      val storageRefrence = FirebaseStorage.getInstance().getReference("users/$fileName")
//
//      storageRefrence.putFile(pickedPhoto!!)






//      val title = binding.foodTitle.text.toString()
////      val userDonor = bindingItem.userDonor.text.toString()
//      val name = binding.name.text.toString()
//      val som = binding.add.text.toString()
//
//      db = FirebaseDatabase.getInstance().getReference("Users")
//
//      val Food = Food(title,name)
//
////      storageRefrence.putStream(Food)
//      db.child(som).setValue(Food ).addOnSuccessListener {
//
//        binding.foodTitle.text.clear()
//        binding.name.text.clear()
//        //needd to clear image as well
//
//        Toast.makeText(this,"Successfully Saved ", Toast.LENGTH_LONG).show()
//      }.addOnFailureListener{
//
//        Toast.makeText(this,"FAiled to store data ", Toast.LENGTH_LONG).show()
//      }




//      val intent = Intent(this, dashboard::class.java)
//      startActivity(intent)
//      finish()
    }

  }
  private fun uploadImage() {


    //check how to access photo from gallery
    imageUri = Uri.parse("android.resource://$packageName/${R.drawable.kiwi}")
//    imageUri = Uri.parse(binding.imageFood.toString())
    storageReference = FirebaseStorage.getInstance().getReference("Users/"+ auth.currentUser?.uid)
    storageReference.putFile(imageUri).addOnSuccessListener{
      Toast.makeText(this,"Photo Uploaded Successfully ", Toast.LENGTH_LONG).show()
    }.addOnFailureListener{
      Toast.makeText(this,"FAiled to Upload data ", Toast.LENGTH_LONG).show()
    }
  }









  fun pickedPhoto(view: View){
    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
      ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }
    else{
      val galleryIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
      startActivityForResult(galleryIntext,2)
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
      val galleryIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
      startActivityForResult(galleryIntext,2)
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if( requestCode == 2 &&  resultCode == RESULT_OK && data!= null){
         pickedPhoto = data?.data!!

        binding.imageFood.setImageURI(pickedPhoto)


//      if(Build.VERSION.SDK_INT >= 28){
//        val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
//        pickedBitmap = ImageDecoder.decodeBitmap(source)
//        foodView?.setImageBitmap(pickedBitmap)
//      }else{
//        pickedBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
//        foodView?.setImageBitmap(pickedBitmap)
//      }
    }

  }



}
