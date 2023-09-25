package com.tarc.edu.etrack.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.tarc.edu.etrack.R
import com.tarc.edu.etrack.databinding.ActivityLoginBinding
import com.tarc.edu.etrack.ui.register.register

class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emaillogin = binding.editTextTextEmailAddressLogin
        val passwordlogin = binding.editTextTextPasswordLogin
        val btnLogin = binding.buttonLogin
        val btnRegister = binding.buttonToRegister

        binding.buttonLogin.setOnClickListener(){
            val email = emaillogin.text.toString()
            val password = passwordlogin.text.toString()

            readData(email, password)
        }

        binding.buttonToRegister.setOnClickListener(){
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }
    }

    private fun readData(emaillogin: String, passwordlogin: String){


    }
}