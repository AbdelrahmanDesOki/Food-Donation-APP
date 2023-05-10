package hu.bme.aut.projLab.dohdzf.fooddonation

import android.Manifest
import android.app.Activity
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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.AddFoodBinding
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

//import kotlinx.android.synthetic.main.add_food.*


class add_food:AppCompatActivity() {
//  private lateinit var dbref : DatabaseReference
//  private lateinit var foodRecyclerView : RecyclerView
    private lateinit var binding: AddFoodBinding
//  private lateinit var foodArraylist : ArrayList<Food>


//    private lateinit var _binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
//   private lateinit var storageReference: StorageReference
//   private lateinit var imageUri: Uri
   var uploadBitmap: Bitmap? = null
//  private lateinit var db : DatabaseReference

  companion object {
    private const val PERMISSION_REQUEST_CODE = 1001
    private const val CAMERA_REQUEST_CODE = 1002
  }


 private lateinit var foodAdapter: FoodAdapter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = AddFoodBinding.inflate(layoutInflater)
    setContentView(binding.root)
    //validation with User id to store data
    auth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid
//    db = FirebaseDatabase.getInstance().getReference("Users")


//     foodView  = binding.imageFood

    binding.imageFood.setOnClickListener {
      try {
        startActivityForResult(
          Intent(MediaStore.ACTION_IMAGE_CAPTURE),
          CAMERA_REQUEST_CODE
        )
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }


    binding.map.setOnClickListener{
      val intent = Intent(this, MapsActivity::class.java)
      startActivity(intent)
      finish()
    }

    binding.add.setOnClickListener {

      sendItem("")

      val intent = Intent(this, dashboard::class.java)
      startActivity(intent)
      finish()
    }

    onreqNeededPerm()
  }

private fun sendItem(imageUrl: String = ""){
  val food = Food(
    FirebaseAuth.getInstance().currentUser!!.uid,
    binding.foodTitle.text.toString(),
    binding.name.text.toString(),
    imageUrl
  )
//  val uid = FirebaseAuth.getInstance().currentUser!!.uid

  var usercollection = FirebaseFirestore.getInstance().collection("Users")

  usercollection
    .add(food)
    .addOnSuccessListener {
    Toast.makeText(this@add_food,"Data Uploaded Successfully ", Toast.LENGTH_LONG).show()
  }
    .addOnFailureListener{
      Toast.makeText(this@add_food,"Failed to Upload Data ", Toast.LENGTH_LONG).show()
    }

}

   fun send(v: View){
    if(uploadBitmap == null){
         sendItem()
    }else{
      try {
          uploadImage()
      }catch (e: java.lang.Exception){
        e.printStackTrace()
      }
    }
  }


@Throws(Exception::class)
  private fun uploadImage() {

    //check how to access photo from gallery
//    imageUri = Uri.parse("android.resource://$packageName/${R.drawable.kiwi}")
////    imageUri = Uri.parse(binding.imageFood.toString())
    val baos = ByteArrayOutputStream()
    uploadBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageInBytes = baos.toByteArray()

    val storageRef = FirebaseStorage.getInstance().getReference("Users")
    val newImage = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8") + ".jpg"
    val newImagesRef = storageRef.child("Users/$newImage")
    newImagesRef.putBytes(imageInBytes)
      .addOnFailureListener { exception ->
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT)
          .show()
      }.addOnSuccessListener { taskSnapshot ->
        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
        newImagesRef.downloadUrl.addOnCompleteListener (object : OnCompleteListener<Uri>{
          override fun onComplete(p0: Task<Uri>) {
            sendItem(p0.result.toString())
          }

        })
      }


//    storageReference = FirebaseStorage.getInstance().getReference("Users/"+ auth.currentUser?.uid)
//    storageReference.putFile(imageUri).addOnSuccessListener{
//      Toast.makeText(this,"Photo Uploaded Successfully ", Toast.LENGTH_LONG).show()
//    }.addOnFailureListener{
//      Toast.makeText(this,"FAiled to Upload data ", Toast.LENGTH_LONG).show()
//    }





  }









  fun onreqNeededPerm(){

    if (ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.CAMERA
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(
          this,
          android.Manifest.permission.CAMERA
        )
      ) {
        Toast.makeText(
          this,
          "I need it for camera", Toast.LENGTH_SHORT
        ).show()
      }

      ActivityCompat.requestPermissions(
        this,
        arrayOf(android.Manifest.permission.CAMERA),
        PERMISSION_REQUEST_CODE
      )
    } else {
      // we already have permission
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when(requestCode){
      PERMISSION_REQUEST_CODE -> {
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
          Toast.makeText(this, "CAMERA perm granted", Toast.LENGTH_SHORT).show()
        }else{
          Toast.makeText(this, "CAMERA perm NOT granted", Toast.LENGTH_SHORT).show()
        }
      }

    }


  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      uploadBitmap = data!!.extras!!.get("data") as Bitmap
      binding.imgAttach.setImageBitmap(uploadBitmap)
      binding.imgAttach.visibility = View.VISIBLE
    }


  }



}
