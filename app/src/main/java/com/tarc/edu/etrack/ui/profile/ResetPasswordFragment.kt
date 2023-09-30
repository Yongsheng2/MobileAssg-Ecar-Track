package com.tarc.edu.etrack.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tarc.edu.etrack.R

class ResetPasswordFragment : Fragment() {

    private lateinit var currentPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var resetButton: Button



    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)

        currentPasswordEditText = view.findViewById(R.id.currentPasswordEditText)
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText)
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText)
        resetButton = view.findViewById(R.id.resetButton)

        val databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance() // Initialize Firebase Authentication

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetButton.setOnClickListener {
            val currentPassword = currentPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Password reset logic
            resetPassword(currentPassword, newPassword, confirmPassword)
        }
    }

    private fun resetPassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Check if the user is signed in
        if (userId != null) {
            // Reauthenticate the user with their current password
            val credential = EmailAuthProvider.getCredential(userId.email!!, currentPassword)

            userId.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        // Reauthentication successful, update the password
                        userId.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    showToast("Password updated successfully")
                                } else {
                                    showToast("Password update failed")
                                }
                            }
                    } else {
                        showToast("Reauthentication failed. Incorrect current password.")
                    }
                }
        } else {
            showToast("User not signed in.")
        }
    }

    private fun showToast(message: String) {
        val context = requireContext()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

