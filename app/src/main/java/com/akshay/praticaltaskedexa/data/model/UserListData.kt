package com.akshay.praticaltaskedexa.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListData(
    var userListItem: ArrayList<UserListItem>
):Parcelable