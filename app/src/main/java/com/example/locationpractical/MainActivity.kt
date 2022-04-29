package com.example.locationpractical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.locationpractical.databinding.ActivityMainBinding
import com.example.locationpractical.service.LocationTracker

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateLocation()
    }

    private fun updateLocation() {
        if (LocationTracker(this).canGetLocation) {
            binding.latti.setText(LocationTracker(this).getLatitude().toString())
            binding.longi.setText(LocationTracker(this).getLongitude().toString())
        }
    }
}


