package hu.bme.aut.projLab.dohdzf.fooddonation

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.AddFoodBinding
import kotlin.properties.Delegates


class add_food:AppCompatActivity() {

  private lateinit var binding: AddFoodBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = AddFoodBinding.inflate(layoutInflater)

    setContentView(binding.root)

    binding.map.setOnClickListener{
      val intent = Intent(this, MapsActivity::class.java)
      startActivity(intent)
      finish()
    }

  }



}
