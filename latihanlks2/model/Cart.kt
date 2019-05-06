package com.ukk.latihanlks2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cart (
    var id: Int,
    var name:String,
    var price:Int,
    var carbo:Int,
    var protein:Int,
    var img:String,
    var qty: Int
): Parcelable