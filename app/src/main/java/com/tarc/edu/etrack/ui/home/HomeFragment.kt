package com.tarc.edu.etrack.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.tarc.edu.etrack.R
import com.tarc.edu.etrack.RecyclerView.MyAdapter
import com.tarc.edu.etrack.databinding.FragmentHomeBinding
import com.tarc.edu.etrack.ui.station_details.StationData
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference.child("Station") // Update the reference to your Firebase database

        val textViewWelcome = view.findViewById<TextView>(R.id.textViewWelcome)
        recyclerView = view.findViewById(R.id.recyclerViewStation)

        // Set up the RecyclerView
        adapter = MyAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        try {
            if (auth.currentUser != null) {
                // User is authenticated, get the username and set the welcome message
                val userId = auth.currentUser?.uid ?: ""
                database.child(userId).child("username").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val username = dataSnapshot.getValue(String::class.java) ?: ""
                        textViewWelcome.text = "Welcome Back, $username"
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                        Log.e("HomeFragment", "Database Error: ${databaseError.message}")
                    }
                })
            } else {
                // User is not authenticated, display a login message
                textViewWelcome.text = "Login to E-track to access more features!"
            }

            // Retrieve and display station data
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val stationList = ArrayList<StationData>()
                    for (stationSnapshot in dataSnapshot.children) {
                        val stationName = stationSnapshot.child("Name").getValue(String::class.java) ?: ""
                        val stationAddress = stationSnapshot.child("StationAddress").getValue(String::class.java) ?: ""
                        val name = stationSnapshot.key ?: ""
                        val openTime = stationSnapshot.child("OpenTime").getValue(String::class.java) ?: ""
                        val closeTime = stationSnapshot.child("CloseTime").getValue(String::class.java) ?: ""

                        val stationData = StationData(stationName, stationAddress, name, openTime, closeTime)
                        stationList.add(stationData)
                    }

                    adapter.setData(stationList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                    Log.e("HomeFragment", "Database Error: ${databaseError.message}")
                }
            })

        } catch (e: Exception) {
            // Handle any other exceptions that might occur
            Log.e("HomeFragment", "Exception: ${e.message}")
        }

        return view
    }
}