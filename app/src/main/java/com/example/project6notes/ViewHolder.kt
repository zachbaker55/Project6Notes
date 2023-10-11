package com.example.project6notes

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/**
 * A representation of a singular Note displayed in the RecyclerView
 */

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameView : TextView = itemView.findViewById(R.id.title)
    var deleteButton : ImageButton = itemView.findViewById(R.id.XButton)
}