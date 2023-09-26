package com.tarc.edu.etrack.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.tarc.edu.etrack.MainActivity
import com.tarc.edu.etrack.R
import com.tarc.edu.etrack.ui.register.RegisterActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var loginAttempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.editTextTextUsernameLogin)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPasswordLogin)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val registerButton = findViewById<Button>(R.id.buttongetRegister) // "Don't have an account?" button

        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Check for empty email or password
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Email and password are required.")
                return@setOnClickListener
            }

            // Password regex pattern: At least one uppercase letter, one number, and minimum length of 6
            val passwordPattern = "^(?=.*[A-Z])(?=.*[0-9]).{6,}\$".toRegex()
            if (!passwordPattern.matches(password)) {
                showToast("Password must contain at least one uppercase letter, one number, and be at least 6 characters long.")
                return@setOnClickListener
            }

            // Limit login attempts to 5
            if (loginAttempts >= 5) {
                showToast("Maximum login attempts reached. Please try again later.")
                return@setOnClickListener
            }

            // Sign in with Firebase Authentication
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    // User logged in successfully
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { exception ->
                    // User login failed, handle the error
                    showToast("Login failed: ${exception.message}")
                    loginAttempts++
                }
        }

        // Set an OnClickListener for the "Don't have an account?" button
        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

