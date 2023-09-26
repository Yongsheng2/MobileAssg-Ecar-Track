//package com.tarc.edu.etrack.ui.login
//
//import android.content.Intent
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import androidx.annotation.RequiresApi
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.tarc.edu.etrack.MainActivity
//import com.tarc.edu.etrack.databinding.ActivityLoginBinding
//import com.tarc.edu.etrack.ui.home.HomeFragment
//import com.tarc.edu.etrack.ui.register.register
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//
//class login : AppCompatActivity() {
//
//    private lateinit var binding: ActivityLoginBinding
//    private lateinit var database: DatabaseReference
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.buttonLogin.setOnClickListener {
//            val username = binding.editTextTextUsernameLogin.text.toString()
//            val password = binding.editTextTextPasswordLogin.text.toString()
//
//            // Authenticate the user and navigate accordingly.
//            if (authenticateUser(username, password)) {
//                val intent = Intent(this@login, MainActivity::class.java)
//                intent.putExtra("username", username)
//                // ...
//                startActivity(intent)
//            } else {
//                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        binding.buttongetRegister.setOnClickListener {
//            val intent = Intent(this@login, register::class.java)
//            startActivity(intent)
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun readData(username: String, password: String) {
//        database = FirebaseDatabase.getInstance().getReference("User")
//        database.child(username).get().addOnSuccessListener {
//            if (it.exists()) {
//                val passwordFromDatabase = it.child("password").value.toString()
//
//                if (password == passwordFromDatabase) {
//                    val intent = Intent(this@login, HomeFragment::class.java)
//                    intent.putExtra("username", username)
//
//                    val currentTime = LocalDateTime.now()
//                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
//                    val formatted = currentTime.format(formatter)
//
//                    val loginRecord = loginrecord(username, formatted)
//
//                    database = FirebaseDatabase.getInstance().getReference("loginrecord")
//                    database.child(username).setValue(loginRecord).addOnSuccessListener {
//                        Toast.makeText(this, "Welcome Back, $username", Toast.LENGTH_SHORT).show()
//                    }
//
//                    updateData(username)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Username does not exists!", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener {
//            Toast.makeText(this, "Username does not exists!", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun updateData(username: String) {
//        database = FirebaseDatabase.getInstance().getReference("PassData")
//
//        val updatedData = mapOf(
//            "username" to username
//        )
//
//        database.updateChildren(updatedData)
//    }
//}
