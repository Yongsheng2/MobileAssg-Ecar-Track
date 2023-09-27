package com.tarc.edu.etrack.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.tarc.edu.etrack.R

class AddCarFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val CarOptions = mutableListOf<String>()
    private val selectedCars = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_car, container, false)

        val spinner = view.findViewById<Spinner>(R.id.spinnerSelectCar)
        val addButton = view.findViewById<Button>(R.id.buttonAddSelectCar)
        val saveButton = view.findViewById<Button>(R.id.buttonSaveCars)

        val database = FirebaseDatabase.getInstance()
        val CarOptionRef = database.getReference("Cars")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, CarOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        CarOptionRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    CarOptions.clear()
                    for (optionSnapshot in dataSnapshot.children) {
                        val option = optionSnapshot.key
                        option?.let {
                            CarOptions.add(option)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

        addButton.setOnClickListener {
            val selectedEV = spinner.selectedItem.toString()
            if (!selectedCars.contains(selectedEV)) {
                selectedCars.add(selectedEV)
                updateListView()
            }
        }

        auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
                val userId = auth.currentUser?.uid ?: ""
                if (userId != null) {
                    val userCarsRef = database.getReference("users").child(userId).child("usercar")
                    userCarsRef.setValue(selectedCars)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(requireContext(), "Cars saved successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "Failed to save Car", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
        }
        return view
    }
    private fun updateListView() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, selectedCars)
        val listView = view?.findViewById<ListView>(R.id.listViewCars)
        listView?.adapter = adapter
        }
}


