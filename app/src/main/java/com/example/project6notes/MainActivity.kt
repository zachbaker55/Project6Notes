package com.example.project6notes

import ConfirmationDialogFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Main Activity
 */
class MainActivity : AppCompatActivity() {
    lateinit var database: AppDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Create a RecyclerView and empty Note list
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var notes: List<Note> = emptyList()

        //Intent to move to NotePage
        val intent = Intent(this, NotePage::class.java)

        //Create an adapter with listeners
        lateinit var adapter : Adapter
        adapter = Adapter(applicationContext, notes, object : Adapter.NoteClickListener {
            override fun onNoteClick(note: Note) {
                //Go to NotePage with info of a specific note when clicked
                intent.putExtra("noteId", note.id)
                startActivity(intent)
            }

            override fun onDeletePress(note: Note) {
                val dialog = ConfirmationDialogFragment()
                dialog.setConfirmationDialogListener(object : ConfirmationDialogFragment.ConfirmationDialogListener {
                    override fun onDialogPositiveClick(dialog: DialogFragment) {
                        //delete a note if positive dialog is clicked
                        GlobalScope.launch(Dispatchers.IO) {
                            database.noteDao().delete(note)
                            notes = database.noteDao().getAllNotes()
                            runOnUiThread {
                                adapter.setData(notes)
                            }
                        }
                    }

                    override fun onDialogNegativeClick(dialog: DialogFragment) {
                        //Do nothing
                    }

                })

                dialog.show(supportFragmentManager, "ConfirmationDialogFragment")
            }

        })
        recyclerView.adapter = adapter

        //Create a database. Update the database with all notes
        database = Room.databaseBuilder(this, AppDatabase::class.java, "notes_database").build()
        GlobalScope.launch(Dispatchers.IO) {
            notes = database.noteDao().getAllNotes()
            adapter.setData(notes)
        }

        //Go to NotePage when AddNote is clicked
        val clickAddNote : Button =findViewById(R.id.addNote)
        clickAddNote.setOnClickListener {
            intent.putExtra("noteId", -1L)
            startActivity(intent)
        }


    }

}