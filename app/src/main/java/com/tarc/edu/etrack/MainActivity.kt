package com.tarc.edu.etrack

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tarc.edu.etrack.databinding.ActivityMainBinding
import com.tarc.edu.etrack.ui.favorite.FavoriteFragment
import com.tarc.edu.etrack.ui.find_station.FindStationFragment
import com.tarc.edu.etrack.ui.home.HomeFragment
import com.tarc.edu.etrack.ui.login.login
import com.tarc.edu.etrack.ui.notifications.NotificationsFragment
import com.tarc.edu.etrack.ui.profile.ProfileFragment
import com.tarc.edu.etrack.ui.splash.splash

class MainActivity : AppCompatActivity() {

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, splash::class.java)
        startActivity(intent)
        finish()

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                    true
                }
                R.id.menu_find_station -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FindStationFragment())
                        .commit()
                    true
                }
                R.id.menu_favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FavoriteFragment())
                        .commit()
                    true
                }
                R.id.menu_notifications -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, NotificationsFragment())
                        .commit()
                    true
                }
                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

    }


}