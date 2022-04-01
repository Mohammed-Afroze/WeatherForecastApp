package com.afroze.weather.ui.dashboard.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.afroze.weather.data.response.Resource
import com.afroze.weather.databinding.FragmentDashBoardBinding
import com.afroze.weather.ui.dashboard.DashBoardActivity
import com.afroze.weather.ui.dashboard.DashBoardViewModel
import com.afroze.weather.utils.updateUi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_weather_data.*
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.IOException
import java.util.*


@ExperimentalSerializationApi
@AndroidEntryPoint
class DashBoardFragment : Fragment() {

    private val TAG = DashBoardFragment::class.java.simpleName
    private var binding: FragmentDashBoardBinding? = null
    private val viewModel: DashBoardViewModel by activityViewModels()
    private val apiKey = "87b29ca3d4018e56f727189384f24a29"
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastKnownLocation: Location = Location("")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocationPermission()
        viewModel.weatherResponse.observe(viewLifecycleOwner) {
            weatherProgressBar.isVisible = false
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "Success Data::${it.value}")
                    updateUi(binding?.root?.rootView!!, it.value)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "Failure Data::${it.errorBody}")
                    viewModel.getOfflineData()
                }
            }
        }
    }


    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getDeviceLocation()
        } else {
            val requestMultiplePermissions =
                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                    permissions.entries.forEach { mapOfPermission ->
                        val locationPermissionGranted = mapOfPermission.value
                        if (locationPermissionGranted) {
                            if (locationPermissionGranted) {
                                getDeviceLocation()
                            }
                        }
                    }
                }

            requestMultiplePermissions.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getDeviceLocation() {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        lastKnownLocation = task.result
                        try {
                            val geocoder = Geocoder(requireContext(), Locale.getDefault())
                            val addresses = geocoder.getFromLocation(
                                lastKnownLocation.latitude,
                                lastKnownLocation.longitude,
                                1
                            )
                            val city = addresses[0].locality
                            (activity as DashBoardActivity).supportActionBar?.title = city
                            viewModel.getWeather(
                                lastKnownLocation.latitude.toString(),
                                lastKnownLocation.longitude.toString(),
                                apiKey
                            )
                        } catch (ex: IOException) {
                            val snackbar = Snackbar
                                .make(
                                    requireView(),
                                    "Could Not Fetch Data Check your Internet",
                                    Snackbar.LENGTH_LONG
                                )
                            snackbar.show()
                            weatherProgressBar.isVisible = false
                            noDataTetview.isVisible = true
                        }

                    } else {
                        weatherProgressBar.isVisible = false
                        val snackbar = Snackbar
                            .make(
                                binding?.root?.rootView!!,
                                "Location Service Not Enabled",
                                Snackbar.LENGTH_LONG
                            )
                            .setAction("Open Setting") {
                                startActivity(
                                    Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                                    )
                                )
                            }
                        snackbar.show()
                    }
                } else {
                    Log.d(TAG, "Current location is null. Using defaults.")
                    Log.e(TAG, "Exception: %s", task.exception)
                    weatherProgressBar.isVisible = false
                    val snackbar = Snackbar
                        .make(
                            binding?.root?.rootView!!,
                            task.exception.toString(),
                            Snackbar.LENGTH_LONG
                        )
                        .setAction("Open Setting") {
                            startActivity(
                                Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                                )
                            )
                        }
                    snackbar.show()
                }
            }

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
            Toast.makeText(this.context, "Some exception occurred", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("Exception: %s", e.message, e)
            Toast.makeText(this.context, "Some exception occurred", Toast.LENGTH_SHORT).show()
        }
    }
}