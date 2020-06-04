package com.example.keepingnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.show_all_notes.*

class MainActivity : AppCompatActivity() {


    private var adapter : NoteAdapter ?=null
    private var noteList =ArrayList<Note>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_all_notes)

        val layoutManager = LinearLayoutManager(applicationContext)

        RecyclerView!!.itemAnimator = DefaultItemAnimator()


        floatingActionButton.setOnClickListener {
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager, "")
        }

        getAllNotes()

        RecyclerView!!.layoutManager = layoutManager


    }


    fun getAllNotes(){


        noteList.clear()

        val uid = FirebaseAuth.getInstance().uid

        val db = Firebase.firestore

        db.collection("/Users/${uid}/notes")
            .addSnapshotListener { snapshot, exception ->
                snapshot?.forEach { documentSnapshot ->

                    var addNote = Note()
                    val document = documentSnapshot.data
                    addNote.title = document.get("title").toString()
                    addNote.description = document.get("description").toString()


                    addNote.idea = document.get("idea").toString().toBoolean()
                    addNote.todo = document.get("todo").toString().toBoolean()
                    addNote.important = document.get("important").toString().toBoolean()

                    noteList.add(addNote)

                    adapter = NoteAdapter(this, noteList)
                    RecyclerView.adapter = adapter

                    adapter!!.notifyDataSetChanged()

                }

            }
    }


    fun showNote(noteToShow: Int) {
        Toast.makeText(this,"Showing note : ${noteToShow}",Toast.LENGTH_SHORT).show()
    }
}
