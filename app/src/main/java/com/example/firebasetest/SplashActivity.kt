package com.example.firebasetest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser == null) {   // no already signed-in user
            startActivity(Intent(this, AuthActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        //close this SplashActivity
        finish()
    }
}