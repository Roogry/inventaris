package com.ukk.latihanlks2.utils

import java.text.NumberFormat
import java.util.*

fun convertRp(harga: Int): String{
    val localeId = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
    return formatRupiah.format(harga.toDouble())
}