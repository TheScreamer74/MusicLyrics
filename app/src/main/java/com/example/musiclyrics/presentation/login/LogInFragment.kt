package com.example.musiclyrics.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class LogIn : AbstractAuthFragment() {

    val RC_SIGN_IN = 120

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            Log.i("User is connected", user.displayName.toString())
            this.findNavController().navigate(LogInDirections.actionLogInToSearchTrackFragment())
        } else {
            // No user is signed in
            Log.i("User is not connected", "Go to connect")
            createSignInIntent()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}