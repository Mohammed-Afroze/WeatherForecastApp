package com.afroze.weather.ui.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.afroze.weather.R
import com.afroze.weather.databinding.ActivityDashboardBinding
import com.afroze.weather.databinding.ToolbarMainBinding
import com.afroze.weather.sync.SyncViewModel
import com.afroze.weather.ui.base.BaseActivity
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class DashBoardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var toolBarBinding: ToolbarMainBinding
    private val viewModel: SyncViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        toolBarBinding = binding.toolbar
        setContentView(binding.root)
        setSupportActionBar(toolBarBinding.toolbarMain)
        viewModel.startWork()

    }

    override fun onResume() {
        super.onResume()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }
}