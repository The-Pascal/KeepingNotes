package com.example.keepingnotes.registerLogin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.keepingnotes.MainActivity
import com.example.keepingnotes.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_loginpage.*
import kotlinx.android.synthetic.main.activity_main.*

class loginpage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage)

        //used to underline text
        val backToRegistration = findViewById<TextView>(R.id.back_to_registration_login)
        backToRegistration.setPaintFlags(backToRegistration.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        login()

        backToRegistration.setOnClickListener{
            val intent = Intent(this , RegistrationPage::class.java)
            startActivity(intent)
        }
    }

    fun login(){
        login_button_login.setOnClickListener{

           login_button_login.startAnimation()

            val email = email_editText_login.text.toString()
            val password = password_editText_login.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Enter all Credentials",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(it.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show()
                        val icon: Bitmap
                        val deepColor = Color.parseColor("#808000")
                        icon = BitmapFactory.decodeResource(resources,R.drawable.tick_icon)
                        login_button_login.doneLoadingAnimation(deepColor,icon)
                        startActivity(Intent(this , MainActivity::class.java))
                    }
                    else
                    {
                        Toast.makeText(this, "Email is badly formatted",Toast.LENGTH_SHORT).show()
                        login_button_login.revertAnimation{
                            register_button_register.setBackgroundResource(R.drawable.rounded_button)
                        }
                        return@addOnCompleteListener
                    }


                }
                .addOnFailureListener {
                    Toast.makeText(this , "Error Logging in !!",Toast.LENGTH_SHORT).show()
                }

        }
    }
}
