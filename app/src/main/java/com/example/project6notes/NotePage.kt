package com.example.project6notes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * NotePage
 */
class NotePage : AppCompatActivity() {
    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_page)

        //Retrieve the database
        database = Room.databaseBuilder(this, AppDatabase::class.java, "notes_database").build()

        var noteId: Long = -1L


        //Get noteId if it exists
        val extras = intent.extras
        if (extras != null) {
            noteId = extras.getLong("noteId")
        }


        //Declare intent and texts
        val intent = Intent(this, MainActivity::class.java)
        val titleText: EditText =findViewById(R.id.title)
        val descriptionText: EditText =findViewById(R.id.description)

        //If NoteId isnt -1L then it isnt empty. Set text
        if (noteId != -1L) {
            GlobalScope.launch(Dispatchers.IO) {
                val note: Note? = database.noteDao().getNoteById(noteId)
                titleText.setText(note?.title)
                descriptionText.setText(note?.description)
            }
        }

        //Save button.
        val clickSaveButton : Button =findViewById(R.id.saveButton)
        clickSaveButton.setOnClickListener {
            //If its blank, just return to main activity with no save
            if (titleText.text.isBlank() && descriptionText.text.isBlank()) {
                startActivity(intent)
            }

            //If noteId is -1L then it is a new note. Create a new note in database
            if (noteId == -1L)  {
                val newNote = Note(title = titleText.text.toString(), description = descriptionText.text.toString())
                GlobalScope.launch(Dispatchers.IO) {
                    database.noteDao().insert(newNote)
                    startActivity(intent)
                }
            } else {
                //otherwise it is an old note. Update the old note in the database
                GlobalScope.launch(Dispatchers.IO) {
                    val note: Note? = database.noteDao().getNoteById(noteId)
                    note?.apply {
                        title = titleText.text.toString()
                        description = descriptionText.text.toString()
                    }
                    note?.let {
                        database.noteDao().update(it)
                        startActivity(intent)
                    }
                }

            }
        }
    }
}