package com.example.mystop

import com.google.gson.annotations.SerializedName

data class Stop(val gtfsId: String,
                 val name: String,
                 val lat: Double,
                 val lon: Double,
                 val zoneId: String,
                 val wheelchairBoarding: String)

data class Data(
    val stops: List<Stop>)

data class Base(
    val data: Data)