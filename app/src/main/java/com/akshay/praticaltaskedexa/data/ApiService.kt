package com.akshay.praticaltaskedexa.data

import com.akshay.praticaltaskedexa.data.model.UserList
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("81ada0361bbd877efb9e")
      fun getUserList(): Single<UserList>
}