package com.example.firebasetest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

const val AUTH_UI_REQUEST_CODE = 100

class AuthActivity : AppCompatActivity() {
    private var messageTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        messageTextView = findViewById(R.id.message_textview)
        startFirebaseAuthUI()
    }

    fun startFirebaseAuthUI() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build()
                    )
                )
                .setLogo(R.drawable.ic_launcher_background)
                .build(),
            AUTH_UI_REQUEST_CODE
        )
    }

    // This is where the result from Firebase UI arrives
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        messageTextView?.text = "Signing in"

        if (requestCode != AUTH_UI_REQUEST_CODE) return
        startAuthenticating(resultCode, data)
    }

    private fun startAuthenticating(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            startActivity(Intent(this, MainActivity::class.java))
        } else handleFailedAuthentication(data)
    }

    private fun handleFailedAuthentication(data: Intent?) {
        // Activities communicate using Intents. In the case of Firebase Auth UI,
        // we want to get the error data to know what precisely has gone wrong.
        // IdpResponse is a class with which we can work and it "extracts" its data
        // from the Intent.
        val response = IdpResponse.fromResultIntent(data)
        val errorMessage = response?.error?.localizedMessage
        messageTextView?.text = if (response == null) "You cancelled the sign in" else errorMessage
    }

}