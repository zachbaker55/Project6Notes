package com.example.project6notes

import androidx.room.*

/**
 * Data class for each note, set up to work in ROOM database
 */

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var title: String,
    var description: String
)


/**
 * ROOM Dao for each Note
 * 5 methods -
 * insert - inserts the Note into the database
 * getAllNotes - gets a List<Note> from the database
 * getNoteById - gets a single Note from its Long id
 * update - updates a Note in the database with new data
 * delete - removes a Note from the database
 */

@Dao
interface NoteDao {

    @Insert
    fun insert(note : Note)

    @Query("SELECT * FROM notes")
    fun getAllNotes() : List<Note>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Long): Note?

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note : Note)
}
