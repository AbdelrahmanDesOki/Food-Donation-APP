package hu.bme.aut.projLab.dohdzf.fooddonation

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.AddFoodBinding
//import kotlinx.android.synthetic.main.add_food.*
import kotlin.properties.Delegates


class add_food:AppCompatActivity() {

    private lateinit var binding: AddFoodBinding
    var pickedPhoto: Uri? = null
    var pickedBitmap: Bitmap ? = null
    var foodView: ImageView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = AddFoodBinding.inflate(layoutInflater)

    setContentView(binding.root)

     foodView  = binding.imageFood

    binding.map.setOnClickListener{
      val intent = Intent(this, MapsActivity::class.java)
      startActivity(intent)
      finish()
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

    if( requestCode ==2 &&  resultCode == RESULT_OK && data!= null){
         pickedPhoto = data.data
      if(Build.VERSION.SDK_INT >= 28){
        val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
        pickedBitmap = ImageDecoder.decodeBitmap(source)
        foodView?.setImageBitmap(pickedBitmap)
      }else{
        pickedBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, pickedPhoto)
        foodView?.setImageBitmap(pickedBitmap)
      }
    }
    super.onActivityResult(requestCode, resultCode, data)
  }



}
