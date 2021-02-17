package com.akshay.praticaltaskedexa.data.model

interface SelectedListener {

    fun addSelectedItem(item:Any,isSelected:Boolean)
    fun isSelectedItem(item:Any,isSelected:Boolean):Boolean
}