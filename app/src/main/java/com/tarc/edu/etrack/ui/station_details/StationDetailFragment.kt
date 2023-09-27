package com.tarc.edu.etrack.ui.station_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.tarc.edu.etrack.R

class StationDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use the stationKey to retrieve station details from Firebase or any other source
        // and display them in the fragment.
        // ...

        val rootView = inflater.inflate(R.layout.fragment_stationdetail, container, false)
        val textViewStationName = rootView.findViewById<TextView>(R.id.textViewStationName)

        return rootView
    }
}