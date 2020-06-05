package com.example.keepingnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.keepingnotes.registerLogin.RegistrationPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.keepingnotes.R
import kotlinx.android.synthetic.main.activity_settings.*

class Profile : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val uid = FirebaseAuth.getInstance().uid

        db.collection("Users").document(uid!!).get().addOnSuccessListener {
            val username = it.data?.get("username").toString()
            val email = it.data?.get("email").toString()
            email_settingspage.text = email
            username_settingspage.text = username

        }

        sign_out.setOnClickListener {
            val intent = Intent(this, RegistrationPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or ( Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            FirebaseAuth.getInstance().signOut()
        }
    }


}
