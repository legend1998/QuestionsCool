package com.example.questionscool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.view.ViewCompat

class SplashScreen : AppCompatActivity() {

    lateinit var logo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        logo = findViewById(R.id.logo_splash)


        ViewCompat.animate(logo).alpha(1f).setDuration(1500)
            .setInterpolator(DecelerateInterpolator(1.2f)).start()

        Handler().postDelayed({
            intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
