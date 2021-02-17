package com.akshay.praticaltaskedexa.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListItem(
    @SerializedName("city")
    var city: String?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("last_name")
    var lastName: String?
):Parcelable