package com.example.project6notes

import androidx.recyclerview.widget.DiffUtil


/**
 *  Implementation for the DiffUtil for notes
 */

class NoteDiffUtil (private val oldList : List<Note>, private val newList : List<Note>)
    : DiffUtil.Callback() {


    /**
     * Returns size of old list
     */

    override fun getOldListSize(): Int {
        return oldList.count()
    }


    /**
     * Returns size of new list
     */

    override fun getNewListSize(): Int {
        return newList.count()
    }

    /**
     * Checks if notes are the same
     */

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }


    /**
     * Updates individual notes if something is changed
     */

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].title != newList[newItemPosition].title -> {
                false
            }
            oldList[oldItemPosition].description != newList[newItemPosition].description -> {
                false
            }
            else -> true
        }
    }
}