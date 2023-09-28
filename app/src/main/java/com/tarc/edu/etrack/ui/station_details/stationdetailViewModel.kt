package com.tarc.edu.etrack.ui.station_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _selectedStation = MutableLiveData<StationData>()
    val selectedStation: LiveData<StationData>
        get() = _selectedStation

    fun selectStation(station: StationData) {
        _selectedStation.value = station
    }
}