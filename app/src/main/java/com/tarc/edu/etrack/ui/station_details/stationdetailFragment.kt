package com.tarc.edu.etrack.ui.station_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarc.edu.etrack.R

class stationdetailFragment : Fragment() {

    companion object {
        private const val ARG_STATION_NAME = "stationName"

        fun newInstance(stationName: String): stationdetailFragment {
            val fragment = stationdetailFragment()
            val args = Bundle()
            args.putString(ARG_STATION_NAME, stationName)
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var viewModel: StationdetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stationdetail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StationdetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}