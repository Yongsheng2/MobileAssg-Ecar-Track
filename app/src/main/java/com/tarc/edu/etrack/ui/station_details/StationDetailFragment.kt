package com.tarc.edu.etrack.ui.station_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.tarc.edu.etrack.R

class StationDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_stationdetail, container, false)

        val name = arguments?.getString("stationName")

        val textViewDtl = rootView.findViewById<TextView>(R.id.textViewDtl)

        // Check if the 'name' is not null and set it as the text for textViewDtl
        if (!name.isNullOrBlank()) {
            textViewDtl.text = name
        } else {
            textViewDtl.text = "Station Name Not Found" // You can change this message if needed
        }

        return rootView
    }


}