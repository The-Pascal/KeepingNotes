package com.example.keepingnotes

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dialog_new_note.*

class DialogNewNote : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!)

        val inflater = activity!!.layoutInflater

        val dialogView = inflater.inflate(R.layout.activity_dialog_new_note,null)

        val editTitle =
            dialogView.findViewById(R.id.editTitle) as EditText

        val editDescription =
            dialogView.findViewById(R.id.editDescription) as EditText

        val checkBoxIdea =
            dialogView.findViewById(R.id.checkBoxIdea) as RadioButton

        val checkBoxTodo =
            dialogView.findViewById(R.id.checkBoxTodo) as RadioButton

        val checkBoxImportant =
            dialogView.findViewById(R.id.checkBoxImportant) as RadioButton

        val btnCancel =
            dialogView.findViewById(R.id.btnCancel) as Button

        val btnOK =
            dialogView.findViewById(R.id.btnOK) as Button


        builder.setView(dialogView).setMessage("Add a new note")

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnOK.setOnClickListener {
            val newNote = Note()

            newNote.title = editTitle.text.toString()

            newNote.description = editDescription.text.toString()

            newNote.idea = checkBoxIdea.isChecked
            newNote.todo = checkBoxTodo.isChecked
            newNote.important = checkBoxImportant.isChecked

            val db = Firebase.firestore
            val uid = FirebaseAuth.getInstance().uid

            db.collection("Users").document("$uid/notes/${newNote.title}")
                .set(newNote)
                .addOnCompleteListener {
                    dismiss()
                }

            val callingActivity = activity as MainActivity?

            callingActivity!!.createNewNote(newNote)



        }

        return builder.create()

    }


}
