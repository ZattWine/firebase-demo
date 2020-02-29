package com.norm.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.w(TAG, "Listen failed.", exception)
                    return@addSnapshotListener
                }

                val users = ArrayList<User>()
                for (doc in snapshot!!) {
                    val user = doc.toObject(User::class.java).withId<User>(doc.id)
                    users.add(user)
                }
                Log.d(TAG, "Current users: $users")

                var msg = ""
                for (user in users) {
                    msg = "${user.email}\n${user.name}\n${user.bio}"
                }
                textView.text = msg
            }
        
        btnLogOut.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }
    }

    companion object {
        private var TAG = MainActivity::class.java.simpleName
    }
}
