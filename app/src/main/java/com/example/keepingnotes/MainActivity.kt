package com.example.keepingnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.show_all_notes.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_all_notes)


        floatingActionButton.setOnClickListener{
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager,"")
        }
    }

    fun createNewNote(n: Note){
        Toast.makeText(this,"${n.title} successfully created",Toast.LENGTH_SHORT).show()
    }
}
