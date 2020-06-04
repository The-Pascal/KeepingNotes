package com.example.keepingnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.show_all_notes.*

class MainActivity : AppCompatActivity() {

    private var noteList =ArrayList<Note>()

    private var adapter: NoteAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_all_notes)


        adapter = NoteAdapter(this, this.noteList!!)
        val layoutManager = LinearLayoutManager(applicationContext)

        RecyclerView!!.layoutManager = layoutManager
        RecyclerView!!.itemAnimator = DefaultItemAnimator()


        RecyclerView!!.adapter = adapter


        floatingActionButton.setOnClickListener{
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager,"")
        }
    }

    fun createNewNote(n: Note){
        noteList!!.add(n)
        adapter!!.notifyDataSetChanged()
    }

    fun showNote(noteToShow: Int) {
        Toast.makeText(this,"Showing note : ${noteToShow}",Toast.LENGTH_SHORT).show()
    }
}
