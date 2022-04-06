package com.santimattius.list.ui.components

import java.text.SimpleDateFormat
import java.util.*

fun Date.asString(): String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(this)