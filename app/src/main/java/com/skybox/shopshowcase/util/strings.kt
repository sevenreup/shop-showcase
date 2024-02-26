package com.skybox.shopshowcase.util

import android.icu.text.NumberFormat

fun Double.formatCurrency(): String {
    return  "MK ${NumberFormat.getInstance().format(this)}"
}