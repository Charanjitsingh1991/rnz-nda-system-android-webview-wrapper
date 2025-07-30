package com.rnz.ndasystem

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        // Hide system UI for full screen experience
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
        
        // Get references to views
        val backgroundImage = findViewById<ImageView>(R.id.backgroundImage)
        val splashLogo = findViewById<ImageView>(R.id.splashLogo)
        val appName = findViewById<TextView>(R.id.splashAppName)
        val loadingProgress = findViewById<ProgressBar>(R.id.loadingProgress)
        
        // Load animations
        val fadeInScale = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale)
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        
        // Start background animation
        Handler(Looper.getMainLooper()).postDelayed({
            backgroundImage.startAnimation(fadeIn)
        }, 100)
        
        // Show logo with professional animation
        Handler(Looper.getMainLooper()).postDelayed({
            splashLogo.visibility = View.VISIBLE
            splashLogo.startAnimation(fadeInScale)
        }, 500)
        
        // Show app name
        Handler(Looper.getMainLooper()).postDelayed({
            appName.visibility = View.VISIBLE
            appName.startAnimation(fadeIn)
        }, 1000)
        
        // Show loading indicator
        Handler(Looper.getMainLooper()).postDelayed({
            loadingProgress.visibility = View.VISIBLE
            loadingProgress.startAnimation(fadeIn)
        }, 1500)
        
        // Start MainActivity after professional timing
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
