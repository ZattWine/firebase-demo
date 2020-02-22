package com.norm.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        mAuth = FirebaseAuth.getInstance()

        // ui elements
        initUiElement()

    }

    private fun initUiElement() {
        mEmail = findViewById(R.id.txtEmail)
        mPassword = findViewById(R.id.txtPassword)

        btn_login.setOnClickListener {

            // validate user email and password is null or empty
            validateEmailAndPassword()

            mAuth.signInWithEmailAndPassword(mEmail.text.toString(), mPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        navigateToMainActivity()

                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "$it.message")
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun validateEmailAndPassword() {
        val email = mEmail.text
        val password = mPassword.text

        if(email.isNullOrEmpty()) {
            mEmail.error = "Email cannot be empty"
            mEmail.requestFocus()
        }

        if (password.isNullOrEmpty()) {
            mPassword.error = "Password cannot be empty"
            mPassword.requestFocus()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isLoggedIn()) {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun isLoggedIn(): Boolean {
        return mAuth.currentUser != null
    }

    companion object {
        private val TAG = AuthActivity::class.java.simpleName
    }
}
