package com.tarc.edu.etrack.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tarc.edu.etrack.R

class UserInfoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_info, container, false)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference.child("users")

        val editTextUsername = view.findViewById<EditText>(R.id.editTextUserInfoUsername)
        val textViewEmail = view.findViewById<TextView>(R.id.textViewInfoEmail)
//        val textViewCarType = view.findViewById<TextView>(R.id.textViewCarType)
        val buttonSave = view.findViewById<Button>(R.id.buttonUserInfoSave)

        val userId = auth.currentUser?.uid ?: ""
        database.child(userId).child("username").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val username = dataSnapshot.getValue(String::class.java)
                    if (username != null) {
                        // Set the username directly in the EditText
                        editTextUsername.setText(username)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })

        database.child(userId).child("email").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.exists()) {
                        val email = dataSnapshot.getValue(String::class.java) ?: ""

                        // Display email in TextView
                        textViewEmail.text = "$email"
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })

        database.child(userId).child("usercar").child("0").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.exists()) {
                        val usercar = dataSnapshot.getValue(String::class.java) ?: ""

//                        textViewCarType.text = "$usercar"
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })

        buttonSave.setOnClickListener {
            val newUsername = editTextUsername.text.toString()
            // Update the username in the database
            database.child(userId).child("username").setValue(newUsername)
        }

        return view
    }
}
