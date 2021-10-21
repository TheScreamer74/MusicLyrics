package com.example.musiclyrics.presentation.login

import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.musiclyrics.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.util.ExtraConstants
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth

abstract class AbstractAuthFragment : Fragment() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }


    protected fun createSignInIntent() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this.requireContext())
            .addOnCompleteListener {
                // ...
            }
    }
    private fun delete() {
        AuthUI.getInstance()
            .delete(this.requireContext())
            .addOnCompleteListener {
                // ...
            }
    }
}