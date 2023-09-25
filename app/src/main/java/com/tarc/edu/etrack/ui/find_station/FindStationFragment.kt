package com.tarc.edu.etrack.ui.find_station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tarc.edu.etrack.R
import com.tarc.edu.etrack.databinding.FragmentHomeBinding

class FindStationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_find_station, container, false)
        return view
    }

}
