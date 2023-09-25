package com.tarc.edu.etrack.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tarc.edu.etrack.databinding.ActivityRegisterBinding

class register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference
    private var emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var passwordValidation = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnRegister = binding.buttonRegister

        binding.buttonRegister.setOnClickListener(){
            val username = binding.editTextUsername.text.toString()
            val email = binding.editTextTextEmailAddressRegister.text.toString()
            val password = binding.editTextTextPasswordRegister.text.toString()
            val reenterpassword = binding.editTextTextRePasswordRegister.text.toString()

            val newUser = user( username, email, password, "User")

            emailValidationCheck(email)
            passwordValidationCheck(password)

            if (password != reenterpassword){
                Toast.makeText(this, "Password Inconsistent!", Toast.LENGTH_SHORT).show()
                onPause()
            }else {
                database = FirebaseDatabase.getInstance().getReference("User")
                database.child(username).get().addOnSuccessListener {
                    if (it.exists()) {
                        val email = it.child("email").value
                        Toast.makeText(this, "Email Already Exists!", Toast.LENGTH_SHORT).show()
                        onPause()
                    } else {
                        database = FirebaseDatabase.getInstance().getReference("User")
                        database.child(username).setValue(newUser).addOnSuccessListener {
                            Toast.makeText(this, "Successfully Register!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { exception ->
                            Toast.makeText(this, "Failure 1: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, "Failure 2: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun passwordValidationCheck(password: String) {
        if(password.matches(passwordValidation.toRegex())){
        }else{
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
            onStop()
        }
    }

    private fun emailValidationCheck(email: String) {
        if(email.matches(emailValidation.toRegex())){
        }else{
            Toast.makeText(this, "Invalid Email Address!", Toast.LENGTH_SHORT).show()
            onPause()
        }
    }
}