package hu.bme.aut.projLab.dohdzf.fooddonation


import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.AddFoodBinding
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.util.*
import kotlin.jvm.Throws



class add_food:AppCompatActivity() {

    private lateinit var binding: AddFoodBinding
    private lateinit var auth: FirebaseAuth
    var uploadBitmap: Bitmap? = null

    companion object {
    private const val PERMISSION_REQUEST_CODE = 1001
    private const val CAMERA_REQUEST_CODE = 1002
     }




  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = AddFoodBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.add.visibility= View.GONE

    //validation with User id to store data
    auth = FirebaseAuth.getInstance()

    binding.imageFood.setOnClickListener {
      try {
        startActivityForResult(
          Intent(MediaStore.ACTION_IMAGE_CAPTURE),
          CAMERA_REQUEST_CODE
        )
      } catch (e: Exception) {
        e.printStackTrace()
      }
      binding.imageFood.visibility= View.GONE
    }


    binding.map.setOnClickListener{

      startActivityForResult(Intent(this, MapsActivity::class.java), 101)

      if(
        binding.foodTitle.text.toString().isNotEmpty() ||
        binding.name.text.toString().isNotEmpty() ||
        binding.emailContact.text.toString().isNotEmpty()
       )
      {
        binding.add.visibility= View.VISIBLE
      }

    }



    binding.add.setOnClickListener {

      if(uploadBitmap == null){
        sendItem("")
      }else{
      try {
          uploadImage()
      }catch (e: java.lang.Exception){
        e.printStackTrace()
      }
    }
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
    imageUrl,
    binding.descriptionFood.text.toString(),
    binding.loc.text.toString(),
    binding.emailContact.text.toString()
  )

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


@Throws(Exception::class)
  private fun uploadImage() {

    val baos = ByteArrayOutputStream()
    uploadBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageInBytes = baos.toByteArray()
    val storageRef = Firebase.storage.reference
    val newImage = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8") + ".jpg"
    val newImagesRef = storageRef.child("Users/$newImage")
    newImagesRef.putBytes(imageInBytes)
      .addOnFailureListener { exception ->
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT)
          .show()
      }.addOnSuccessListener { taskSnapshot ->
        newImagesRef.downloadUrl.addOnCompleteListener (object : OnCompleteListener<Uri>{
          override fun onComplete(p0: Task<Uri>) {
            sendItem(p0.result.toString())
          }

        })
      }
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
          Toast.makeText(this, "CAMERA permission is granted", Toast.LENGTH_SHORT).show()
        }else{
          Toast.makeText(this, "CAMERA permission is NOT granted", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    //used for camera data
    if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      uploadBitmap = data!!.extras!!.get("data") as Bitmap
      binding.imgAttach.setImageBitmap(uploadBitmap)
      binding.imgAttach.visibility = View.VISIBLE
    }
    //used for maps data
    if(requestCode == 101 && resultCode == Activity.RESULT_OK){

      var location = data!!.getStringExtra("loc")
      binding.loc.text=location.toString()

    }
  }
}
