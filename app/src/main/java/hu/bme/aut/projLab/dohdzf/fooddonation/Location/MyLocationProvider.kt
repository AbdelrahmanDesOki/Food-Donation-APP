package hu.bme.aut.projLab.dohdzf.fooddonation.Location

import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*


class MyLocationProvider(
  context: Context,
  private val onNewLocationAvailable: OnNewLocationAvailable
) {

  interface OnNewLocationAvailable {
    fun onNewLocation(location: Location)
  }

  //Initialize fused location
  private val fusedLocationClient:
    FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

  private var locationCallback: LocationCallback = object : LocationCallback() {
    override fun onLocationResult(locationResult: LocationResult) {
      super.onLocationResult(locationResult)
      locationResult.lastLocation?.let { onNewLocationAvailable.onNewLocation(it) }
    }
  }


  @Throws(SecurityException::class)
  fun startLocationMonitoring() {
    val locationRequest = LocationRequest()
    locationRequest.interval = 1000
    locationRequest.fastestInterval = 500
    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    fusedLocationClient.requestLocationUpdates(locationRequest,
      locationCallback, Looper.myLooper())

  }

  @Throws(SecurityException::class)
  fun stopLocationMonitoring() {
    fusedLocationClient.removeLocationUpdates(locationCallback)
  }

}
