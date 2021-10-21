package com.example.musiclyrics.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class LogIn : AbstractAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}