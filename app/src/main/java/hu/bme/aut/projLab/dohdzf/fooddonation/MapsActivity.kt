package hu.bme.aut.projLab.dohdzf.fooddonation

import android.content.pm.PackageManager
import android.location.Geocoder
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ActivityMapsBinding
import java.util.*


//Need to fix how to move marker on map
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap : GoogleMap
    private lateinit var currentlocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 1000
    lateinit var currentMarker: Marker

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

    val getLocation = fusedLocationProviderClient?.lastLocation

      getLocation?.addOnSuccessListener {
      location ->
      if(location != null ){
        this.currentlocation = location

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
     mMap = googleMap

      val latlong = LatLng(currentlocation?.latitude!!,  currentlocation?.longitude!!)
      drawMarker(latlong)

      mMap.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener{

        override fun onMarkerDrag(p0: Marker) {

        }
        override fun onMarkerDragEnd(p0: Marker) {
           if(currentMarker!=null)
             currentMarker?.remove()

             val newLatLng = LatLng(p0?.position!!.latitude, p0?.position!!.longitude)
             drawMarker(newLatLng)
        }

        override fun onMarkerDragStart(p0: Marker) {

        }
      } )
    }

  private fun drawMarker(latlng: LatLng){
    val markerOptions = MarkerOptions().position(latlng).title("Current Location")
        .snippet(getAddress(latlng.latitude, latlng.longitude))
          .draggable(true)
          mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng))
          mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 7f))
          currentMarker = mMap.addMarker(markerOptions)!!
          currentMarker?.showInfoWindow()
  }

  private fun getAddress(lat: Double, lon: Double) : String?  {
   val geocoder = Geocoder(this, Locale.getDefault())
    val address =  geocoder.getFromLocation(lat,lon, 1)
    return address[0].getAddressLine(0).toString()
  }
}
