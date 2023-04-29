package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var currentlocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getCurrentLocationUser()
    }

  private fun getCurrentLocationUser(){
    if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
      ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    ){
      ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
      return
    }

    val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener {
      location ->

      if(location != null ){
        currentlocation = location

        Toast.makeText(applicationContext, currentlocation.latitude.toString() + " " + currentlocation.longitude.toString(), Toast.LENGTH_LONG ).show()

        val mapFragment = supportFragmentManager
          .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
      }
    }

  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when(requestCode){
      permissionCode -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
        getCurrentLocationUser()
      }
    }
  }

    override fun onMapReady(googleMap: GoogleMap) {

      val latlng = LatLng(currentlocation.latitude,  currentlocation.longitude)
      val markerOptions= MarkerOptions().position(latlng).title("Current Location")

      googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latlng))
      googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 7f))
      googleMap?.addMarker(markerOptions)



    }
}
