package com.tarc.edu.etrack.ui.station_details

data class StationData(
    val stationName: String,
    val openTime: Long,
    val closeTime: Long,
    val stationAddress: String,
    val imageUrl: String // Firebase Storage image URL
)

