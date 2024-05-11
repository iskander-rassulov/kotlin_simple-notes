package com.example.simplenotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private var notes: List<Note>, private val onClick: (Note) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View, val onClick: (Note) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)

        fun bind(note: Note) {
            noteTextView.text = note.content
            itemView.setOnClickListener {
                onClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount() = notes.size

    fun updateNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }
}