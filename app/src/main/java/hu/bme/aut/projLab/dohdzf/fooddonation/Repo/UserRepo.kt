package hu.bme.aut.projLab.dohdzf.fooddonation.Repo


import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import hu.bme.aut.projLab.dohdzf.fooddonation.Food
import java.lang.Exception
import java.time.Instant


class UserRepo {


private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Useres")

  @Volatile private var  INSTANCE : UserRepo ?= null

  fun getInstance(): UserRepo{

     return INSTANCE ?: synchronized(this){

       val instance = UserRepo()
         INSTANCE = instance
         instance
     }

  }


  fun loadUsers(userList: MutableLiveData<List<Food> >){
       databaseReference.addValueEventListener( object : ValueEventListener{
         override fun onDataChange(snapshot: DataSnapshot) {
           try {

             val _userList: List<Food> = snapshot.children.map {
               dataSnapshot -> dataSnapshot.getValue(Food::class.java)!!
             }
                userList.postValue(_userList)

           }catch (e: Exception){

           }
         }

         override fun onCancelled(error: DatabaseError) {
           TODO("Not yet implemented")
         }


       })
  }







}
