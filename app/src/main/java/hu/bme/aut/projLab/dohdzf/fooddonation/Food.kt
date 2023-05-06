package hu.bme.aut.projLab.dohdzf.fooddonation

import android.location.Location
import android.net.Uri
import android.widget.ImageView

data class Food(
  var uid: String ?= null ,
  var titleFood: String ?= null,
  var userDonor: String ?= null,
  var imgUrl: String ?= null
      )
