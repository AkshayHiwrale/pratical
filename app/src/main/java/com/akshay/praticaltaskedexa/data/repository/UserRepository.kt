package com.akshay.praticaltaskedexa.data.repository

import com.akshay.praticaltaskedexa.data.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(val apiService: ApiService){

       fun getUserList() = apiService.getUserList()
}