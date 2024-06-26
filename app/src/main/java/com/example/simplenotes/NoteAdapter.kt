package com.example.simplenotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private var notes: List<Note>, private val onClick: (Note) -> Unit, private val onLongClick: (Note) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    class NoteViewHolder(itemView: View, val onClick: (Note) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val noteNameTextView: TextView = itemView.findViewById(R.id.noteNameTextView)
        private val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)

        fun bind(note: Note) {
            noteNameTextView.text = note.name
            noteTextView.text = note.content
            itemView.setOnClickListener {
                onClick(note)
            }
        }
    }


//    class NoteViewHolder(itemView: View, val onClick: (Note) -> Unit) : RecyclerView.ViewHolder(itemView) {
//        private val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
//        private val editIcon: ImageView = itemView.findViewById(R.id.editIcon)
//
//        fun bind(note: Note) {
//            noteTextView.text = note.content
//            editIcon.setOnClickListener {
//                onClick(note)
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount() = notes.size

    fun updateNotes(newNotes: List<Note>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = notes.size
            override fun getNewListSize() = newNotes.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return notes[oldItemPosition].id == newNotes[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return notes[oldItemPosition] == newNotes[newItemPosition]
            }
        })

        this.notes = newNotes
        diffResult.dispatchUpdatesTo(this)
    }



}