package com.akshay.praticaltaskedexa.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akshay.praticaltaskedexa.data.model.UserListItem
import com.akshay.praticaltaskedexa.data.repository.UserRepository
import com.akshay.praticaltaskedexa.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel @ViewModelInject constructor(private val userRepository: UserRepository) : ViewModel() {
    val userList: MutableLiveData<Resource<MutableList<UserListItem>>> = MutableLiveData()
    val selectedUser: MutableLiveData<MutableList<UserListItem>> = MutableLiveData()
    val currentTab:MutableLiveData<Int> = MutableLiveData()
    val count:MutableLiveData<Int> = MutableLiveData(0)

    private val compositeDisposable = CompositeDisposable()


 fun getUserList(){
     try {
         compositeDisposable.add(
             userRepository.getUserList()
                 .subscribeOn(Schedulers.io())
                 .subscribe(
                     {
                         userList.postValue(Resource.success(it?.toMutableList()))
                     },
                     {
                         userList.postValue(Resource.error())
                         it.message?.let { it1 -> Log.e("UserDataSource", it1) }
                     }
                 )
         )

     } catch (e: Exception) {
         e.message?.let { Log.e("UserDataSource", it) }
     }



 }




}
