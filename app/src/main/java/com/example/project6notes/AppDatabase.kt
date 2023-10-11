package com.example.project6notes

import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * The database for Note
 */

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
