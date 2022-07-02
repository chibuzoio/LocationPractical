package com.example.locationpractical

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.locationpractical.databinding.ActivityMainBinding
import com.example.locationpractical.service.LocationTracker
import java.io.IOException
import java.lang.IndexOutOfBoundsException
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.refresh.setOnClickListener {
            updateLocation()
        }

        updateLocation()

        if (LocationTracker(this).canGetLocation) {
            val latitude = LocationTracker(this).getLatitude()
            val longitude = LocationTracker(this).getLongitude()

            val addresses: List<Address>
            val geocoder = Geocoder(this, Locale.getDefault())

            try {
                addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                val address: String =
                    addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName

                Log.e(
                    TAG, "Current location properties here are city = $city, " +
                            "state = $state, country = $country, postal code = $postalCode " +
                            "and known name = $knownName"
                )
            } catch (exception: Exception) {
                when (exception) {
                    is IOException -> {
                        Log.e(TAG, "IOException was caught, with message = ${exception.message}")
                    }
                    is IndexOutOfBoundsException -> {
                        Log.e(TAG, "IndexOutOfBoundsException was caught, with message = ${exception.message}")
                    }
                    else -> {
                        Log.e(TAG, "Error message from here is ${exception.message}")
                    }
                }
            }
        }
    }

    private fun updateLocation() {
        if (LocationTracker(this).canGetLocation) {
            binding.latti.setText(LocationTracker(this).getLatitude().toString())
            binding.longi.setText(LocationTracker(this).getLongitude().toString())
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}


