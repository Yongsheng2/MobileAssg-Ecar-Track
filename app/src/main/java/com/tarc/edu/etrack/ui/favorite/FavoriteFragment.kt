package com.tarc.edu.etrack.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.tarc.edu.etrack.RecyclerView.StationNavigator
import com.tarc.edu.etrack.databinding.FragmentHomeBinding
import com.tarc.edu.etrack.ui.station_details.StationData
import com.tarc.edu.etrack.ui.station_details.StationDetailFragment
import java.util.*

class FavoriteFragment : Fragment(), StationNavigator {

    override fun navigateToStationDetail(stationName: String) {

        navigateToAnotherFragment(stationName)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var adapter: MyAdapter
    fun navigateToAnotherFragment(selectedStationName: String) {
        val fragment = StationDetailFragment() // Replace with the actual name of the fragment you want to navigate to.

        // Pass the selectedStationName to the new fragment
        val bundle = Bundle()
        bundle.putString("stationName", selectedStationName)
        fragment.arguments = bundle

        // Use FragmentTransaction to navigate to the new fragment
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment) // Replace 'fragmentContainer' with your actual container ID
        transaction.addToBackStack(null) // Add to back stack if needed
        transaction.commit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference.child("Station") // Update the reference to your Firebase database

        adapter = MyAdapter({ selectedStationName ->
            navigateToAnotherFragment(selectedStationName)
        }, this)
        // Set up the RecyclerView

        binding.recyclerViewStation.adapter = adapter
        binding.recyclerViewStation.layoutManager = LinearLayoutManager(requireContext())

        try {
            val userId = auth.currentUser?.uid ?: ""

            // First, fetch the favorites for the logged-in user
            database.child("users").child(userId).child("favorites")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val stationList = ArrayList<StationData>()
                        for (favoriteSnapshot in dataSnapshot.children) {
                            if (favoriteSnapshot.getValue(Boolean::class.java) == true) {
                                val stationName = favoriteSnapshot.key ?: ""
                                // Fetch station data from the Station node
                                database.child("Station").child(stationName)
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(stationSnapshot: DataSnapshot) {
                                            val name = stationSnapshot.key ?: ""
                                            val stationName = stationSnapshot.child("Name").getValue(String::class.java) ?: ""
                                            val openTime = stationSnapshot.child("OpenTime").getValue(String::class.java) ?: ""
                                            val closeTime = stationSnapshot.child("CloseTime").getValue(String::class.java) ?: ""
                                            val stationData = StationData(stationName, name, openTime, closeTime)
                                            stationList.add(stationData)

                                            adapter.setData(stationList)
                                            binding.recyclerViewStation.adapter = adapter
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {
                                            Log.e("FavouriteFragment", "Database Error: ${databaseError.message}")
                                        }
                                    })
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e("FavouriteFragment", "Database Error: ${databaseError.message}")
                    }
                })

        } catch (e: Exception) {
            Log.e("FavouriteFragment", "Exception: ${e.message}")
        }

        return binding.root
    }



}