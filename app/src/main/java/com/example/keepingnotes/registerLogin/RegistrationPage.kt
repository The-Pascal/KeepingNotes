package com.example.keepingnotes.registerLogin

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.keepingnotes.MainActivity
import com.example.keepingnotes.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class RegistrationPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //used to underline text
        val alreadyhaveaccount = findViewById<TextView>(R.id.already_account_register)
        alreadyhaveaccount.setPaintFlags(alreadyhaveaccount.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        val registerbtn = findViewById<Button>(R.id.register_button_register)

        //registration button click
        registerbtn.setOnClickListener{

            register()
        }

        //already have an account click listener
        already_account_register.setOnClickListener{
            val myintent = Intent(this, loginpage::class.java)
            startActivity(myintent)
        }


        verifyUserIsLoggedIn()

    }


    fun register(){

        val email = email_editText_register.text.toString()
        val password = password_editText_register.text.toString()
        Log.e("register","Email: $email and password: $password")

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this , "Enter all fields first ",Toast.LENGTH_SHORT).show()
            return
        }

        var auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(!it.isSuccessful){
                    return@addOnCompleteListener
                }
                else{
                    Toast.makeText(this,"Account successfully created with uid : ${it.result?.user?.uid} ",Toast.LENGTH_SHORT).show()
                    saveUserToFirebaseDatabase()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error creating account",Toast.LENGTH_SHORT).show()
            }

    }


    private fun saveUserToFirebaseDatabase(){
        val uid = FirebaseAuth.getInstance().uid?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")
        val users = Users(
            uid,
            username_editText_register.text.toString()
        )
        ref.setValue(users)
            .addOnSuccessListener {

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or ( Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }

    private fun verifyUserIsLoggedIn(){

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or ( Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


    }
}

@Parcelize
class Users(val uid: String , val username: String ): Parcelable{
    constructor(): this("","")
}
