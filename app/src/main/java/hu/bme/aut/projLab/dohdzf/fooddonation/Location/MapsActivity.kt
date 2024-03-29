package hu.bme.aut.projLab.dohdzf.fooddonation.Location

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import hu.bme.aut.projLab.dohdzf.fooddonation.DataClass.Food
import hu.bme.aut.projLab.dohdzf.fooddonation.R
import hu.bme.aut.projLab.dohdzf.fooddonation.databinding.ActivityMapsBinding
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
  MyLocationProvider.OnNewLocationAvailable {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap : GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var myLocationProvider: MyLocationProvider
    private lateinit var locationString: String
    private val permissionCode = 101
    lateinit var currentMarker: Marker
    var food: Food?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //using fused location to able to provide location based on GPS, WIFI and Cellular
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getCurrentLocationUser()

        binding.getLocation.setOnClickListener {
          //sending location data to another layout
          var intent = Intent()
          intent.putExtra("loc", binding.locationText.text.toString())
          setResult(Activity.RESULT_OK, intent)
          finish()
        }

    }

  private fun getCurrentLocationUser(){

    if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
      ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    ){
      //getting permission to be able to access the location.
      ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
      myLocationProvider = MyLocationProvider(this, this)
      myLocationProvider.startLocationMonitoring()
      return
    }
        val mapFragment = supportFragmentManager
          .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
      //sending specific long and lat to be the start point for the maps marker.
      val latlong = LatLng(47.4733781,19.059865)
      drawMarker(latlong)

      mMap.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener{

        override fun onMarkerDrag(p0: Marker) { }
        override fun onMarkerDragEnd(p0: Marker) {
           if(currentMarker!=null)
             currentMarker?.remove()
             val newLatLng = LatLng(p0?.position!!.latitude, p0?.position!!.longitude)
             drawMarker(newLatLng)
        }
        override fun onMarkerDragStart(p0: Marker) { }
      } )
    }

  private fun drawMarker(latlng: LatLng){
    //showing current location above marker.
    val markerOptions = MarkerOptions().position(latlng).title("Current Location")
        .snippet(getAddress(latlng.latitude, latlng.longitude))
          .draggable(true)

      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
          mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng))
          mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 7f))
          currentMarker = mMap.addMarker(markerOptions)!!
          currentMarker?.showInfoWindow()

  }

  private fun getAddress(lat: Double, lon: Double) : String?  {
    //return string that contain the exact location.
   val geocoder = Geocoder(this, Locale.getDefault())
    val address =  geocoder.getFromLocation(lat,lon, 1)
      locationString = address[0].getAddressLine(0).toString()
      binding.locationText.text=locationString

    return  locationString
  }


  override fun onNewLocation(location: Location) {
    var prevLocation: Location? = null
    var distance: Float = 0f
    if (location.accuracy < 25) {
      if (prevLocation != null) {
        if (location.distanceTo(prevLocation)>3) {
          distance += location.distanceTo(prevLocation)
        }
      }
    }
    //change the view based on the marker location.
    mMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
      locationString = data!!.extras!!.get("data").toString()
      binding.locationText.text = locationString
      food?.address =binding.locationText.text.toString()

    }
  }

}
