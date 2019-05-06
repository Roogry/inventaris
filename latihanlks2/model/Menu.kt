package com.ukk.latihanlks2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu (
    var name:String,
    var price:Int,
    var carbo:Int,
    var protein:Int,
    var img:String
): Parcelable