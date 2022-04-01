package com.afroze.weather.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.afroze.weather.databinding.ActivitySplashScreenBinding
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this, DashBoardActivity::class.java))
            finish()
        }, 2000)
    }
}