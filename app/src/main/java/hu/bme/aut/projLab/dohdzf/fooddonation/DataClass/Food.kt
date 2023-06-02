package hu.bme.aut.projLab.dohdzf.fooddonation.DataClass

import java.io.Serializable

//Data Class that is send to Firebase
data class Food(
  var uid: String ?= null ,
  var titleFood: String ?= null,
  var userDonor: String ?= null,
  var imgUrl: String ?= null,
  var description: String ?= null,
  var address: String ?= null,
  var email: String ?= null,
  var availableItems: String ?= null
      ): Serializable
