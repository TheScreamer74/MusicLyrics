package com.example.musiclyrics.logIn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.musiclyrics.R
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class LogIn : Fragment() {

    val RC_SIGN_IN = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //userConnect()
        FacebookSdk.sdkInitialize(this.context)
        sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this.activity)

        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(true)
                .setLogo(R.drawable.album_template)
                .build(), RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Log.i("Result Login", user?.displayName.toString())

                this.findNavController().navigate(LogInDirections.actionLogInToSearchTrackFragment())
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    fun userConnect() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            Log.i("User is connected", user.displayName.toString())
            this.findNavController().navigate(LogInDirections.actionLogInToSearchTrackFragment())
        } else {
            // No user is signed in
            Log.i("User is not connected", "Go to connect")
        }
    }

}