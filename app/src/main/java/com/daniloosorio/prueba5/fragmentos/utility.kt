package com.daniloosorio.prueba5.fragmentos

import android.content.Context
import android.widget.Toast


var LAT: String =""
var LON: String =""
fun showtemp(lat: String, lon: String,context: Context) {
    LAT=lat
    LON=lon
    Toast.makeText(context, "$lat , $lon", Toast.LENGTH_SHORT).show()
}

