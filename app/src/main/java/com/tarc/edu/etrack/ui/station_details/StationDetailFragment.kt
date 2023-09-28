package com.tarc.edu.etrack.ui.station_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tarc.edu.etrack.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Locale

class StationDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_stationdetail, container, false)

        val name = arguments?.getString("stationName")

        // Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val stationRef = database.getReference("Station/$name") // Adjust the reference path

        stationRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val stationname = dataSnapshot.child("Name").getValue(String::class.java)
                val address = dataSnapshot.child("StationAddress").getValue(String::class.java)
                val openTime = dataSnapshot.child("OpenTime").getValue(String::class.java) ?: ""
                val closeTime = dataSnapshot.child("CloseTime").getValue(String::class.java) ?: ""

                val textViewName = rootView.findViewById<TextView>(R.id.textViewStationName)
                textViewName.text = stationname

                val textViewAddress = rootView.findViewById<TextView>(R.id.textViewStationAddress)
                textViewAddress.text = address

                val textViewStatus = rootView.findViewById<TextView>(R.id.textViewStatus)

                val currentTime = "09:30" // Replace with your actual current time

                val stationOpen = isStationOpen(currentTime, openTime, closeTime)
                if (stationOpen) {
                    textViewStatus.text = "Open"
                } else {
                    textViewStatus.text = "Closed"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })

        // Firebase Storage for Images

        val stationImage1 = rootView.findViewById<ImageView>(R.id.imageViewDetail1)
        val stationImage2 = rootView.findViewById<ImageView>(R.id.imageViewDetail2)
        val stationImage3 = rootView.findViewById<ImageView>(R.id.imageViewDetail3)

        // Load images using Glide (adjust the path as per your Firebase Storage structure)
        Glide.with(this)
            .load("gs://e-car-track-d4ed3.appspot.com/StationImage/$name.jpg")
            .override(200, 200) // Set the desired dimensions here
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(stationImage1)

        Glide.with(this)
            .load("gs://e-car-track-d4ed3.appspot.com/StationImage/${name}1.jpg")
            .override(200, 200) // Set the desired dimensions here
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(stationImage2)

        Glide.with(this)
            .load("gs://e-car-track-d4ed3.appspot.com/StationImage/${name}2.jpg")
            .override(200, 200) // Set the desired dimensions here
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(stationImage3)

        return rootView
    }

    private fun isStationOpen(currentTime: String, openTime: String, closeTime: String): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTimeDate = sdf.parse(currentTime)
        val openTimeDate = sdf.parse(openTime)
        val closeTimeDate = sdf.parse(closeTime)

        return currentTimeDate.after(openTimeDate) && currentTimeDate.before(closeTimeDate)
    }
}
