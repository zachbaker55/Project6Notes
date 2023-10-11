package com.example.project6notes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter adapts Note data into ViewHolder note_card
 */
class Adapter(private val context: Context, private var notes: List<Note>, private val listener: NoteClickListener) : RecyclerView.Adapter<ViewHolder>() {

    /**
     * Creates a ViewHolder for the RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_card, parent, false)
        return ViewHolder(view)
    }

    /**
     * Binds the ViewHolder's data to a Note
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = notes[position].title

        holder.itemView.setOnClickListener {
            listener.onNoteClick(notes[position])
        }

        holder.deleteButton.setOnClickListener {
            listener.onDeletePress(notes[position])
        }
    }

    /**
     * Returns number of Notes in adapter
     */
    override fun getItemCount(): Int {
        return notes.size
    }

    /**
     * DiffUtil update the ViewHolder
     */
    fun setData(newNoteList : List<Note>) {
        val diffUtil = NoteDiffUtil(notes, newNoteList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        notes = newNoteList
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Listeners for clicking on the note_card
     */
    interface NoteClickListener {
        fun onNoteClick(note: Note)

        fun onDeletePress(note: Note)
    }


}
