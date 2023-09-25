package com.tarc.edu.etrack.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.view.View
import com.tarc.edu.etrack.databinding.ActivityLoginBinding
import com.tarc.edu.etrack.ui.home.HomeFragment
import com.tarc.edu.etrack.ui.register.register
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference
    private var username = ""
    private var password = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernamelogin = binding.editTextTextUsernameLogin
        val passwordlogin = binding.editTextTextPasswordLogin

        binding.buttonLogin.setOnClickListener(){
            val username = usernamelogin.text.toString()
            val password = passwordlogin.text.toString()

            readData(username, password)
        }

        binding.buttongetRegister.setOnClickListener(){
            val intent = Intent(this@login, register::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun readData(usernamelogin: String, passwordlogin: String) {
        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(usernamelogin).get().addOnSuccessListener {
            if (it.exists()) {
                val password = it.child("password").value

                if (passwordlogin == password) {
                    val intent = Intent(this@login, HomeFragment::class.java)
                    intent.putExtra("username", usernamelogin)
                    val un = binding.editTextTextUsernameLogin
                    val currenttime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                    val formatted = currenttime.format(formatter)

                    val logRecord = loginrecord(usernamelogin, formatted)

                    database = FirebaseDatabase.getInstance().getReference("loginrecord")
                    database.child(usernamelogin).setValue(logRecord).addOnSuccessListener {
                        Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show()
                    }
                    updateData(un.text.toString())
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Username does not exists!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Username does not exists!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData(e: String){
        var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("PassData")

        val up = mapOf<String, String>("username" to e,)
        database.updateChildren(up)

//        val parentLayout: android.view.View = findViewById(android.R.id.content)
//        val Snackbar = Snackbar.make(parentLayout, "refer success !", Snackbar.LENGTH_SHORT)
//        Snackbar.show()
    }
}