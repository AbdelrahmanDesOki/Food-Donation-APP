package hu.bme.aut.projLab.dohdzf.fooddonation.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.projLab.dohdzf.fooddonation.Food
import hu.bme.aut.projLab.dohdzf.fooddonation.Repo.UserRepo


class UserViewModel: ViewModel() {

private val repo: UserRepo
private val _allusers = MutableLiveData<List<Food>>()
  val allUsers: LiveData<List<Food>> = _allusers

  init {
      repo = UserRepo().getInstance()
      repo.loadUsers(_allusers)
  }


}
