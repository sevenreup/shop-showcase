package com.skybox.shopshowcase.util

import java.text.SimpleDateFormat
import java.util.Date

fun Date.format(): String {
    return SimpleDateFormat("dd/MM/yyyy").format(this)
}