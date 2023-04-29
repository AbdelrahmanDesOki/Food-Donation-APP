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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.AddFoodBinding


class add_food:AppCompatActivity(), OnMapReadyCallback {

  private lateinit var binding: AddFoodBinding
//  private lateinit var map: GoogleMap
lateinit var map_View: MapView

val permissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = AddFoodBinding.inflate(layoutInflater)
    setContentView(binding.root)

    //need to be checked again

     map_View = binding.mapView
    //Issue is here.
    map_View.getMapAsync(this)
    map_View.onCreate(savedInstanceState)




    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      // Location Services are disabled, show an error message or prompt the user to enable them
      Toast.makeText(this, "Please Open your location services", Toast.LENGTH_SHORT).show()
    }
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
      != PackageManager.PERMISSION_GRANTED) {
      var REQUEST_LOCATION_PERMISSION = 0
      ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
    } else {
      // Location permission is granted, get the current location
      getCurrentLocation()
    }


    //Check if permission is granted or not

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
      ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    ) {
      // Permission granted, do something


    } else {
      // Permission not granted, request it
      ActivityCompat.requestPermissions(this, permissions, 123)
    }

  }

  override fun onMapReady(googleMap: GoogleMap) {
    TODO("Not yet implemented")
//    map = googleMap

  }

  override fun onStart() {
    super.onStart()
     map_View.onStart()
  }
  override fun onResume() {
    super.onResume()
    map_View.onResume()
  }

  override fun onPause() {
    super.onPause()
    map_View.onPause()
  }

  override fun onStop() {
    super.onStop()
    map_View.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    map_View.onDestroy()
  }

  override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    super.onSaveInstanceState(outState, outPersistentState)
    map_View.onSaveInstanceState(outState)
  }

  override fun onLowMemory() {
    super.onLowMemory()
    map_View.onLowMemory()
  }







  private fun getCurrentLocation() {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return
    }
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
  }

  private val locationListener = object : LocationListener {
    override fun onLocationChanged(location: Location) {
      // Location changed, get the new location
      val latitude = location.latitude
      val longitude = location.longitude
      // Do something with the latitude and longitude
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
  }


}
