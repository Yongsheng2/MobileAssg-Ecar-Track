package com.tarc.edu.etrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.tarc.edu.etrack.R
import com.tarc.edu.etrack.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference.child("users") // Update the reference to your Firebase database

        val textViewWelcome = view.findViewById<TextView>(R.id.textViewWelcome)

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
                }
            })
        } else {
            // User is not authenticated, display a login message
            textViewWelcome.text = "Login to E-track to access more features!"
        }

        return view
    }
}
