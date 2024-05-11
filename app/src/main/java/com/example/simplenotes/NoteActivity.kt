package com.example.simplenotes

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NoteActivity : AppCompatActivity() {

    private lateinit var noteNameEditText: EditText
    private lateinit var noteEditText: EditText
    private lateinit var saveNoteButton: Button
    private lateinit var deleteNoteButton: Button
    private lateinit var cancelNoteButton: Button
    private var noteId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        noteNameEditText = findViewById(R.id.noteNameEditText)
        noteEditText = findViewById(R.id.noteEditText)
        saveNoteButton = findViewById(R.id.saveNoteButton)
        deleteNoteButton = findViewById(R.id.deleteNoteButton)
        cancelNoteButton = findViewById(R.id.cancelNoteButton)

        val repository = Repository(this)

        noteId = intent.getIntExtra("noteId", -1)
        if (noteId != -1) {
            val note = repository.loadNotes().find { it.id == noteId }
            note?.let {
                noteNameEditText.setText(it.name)
                noteEditText.setText(it.content)
                deleteNoteButton.visibility = View.VISIBLE
            }
        }

        saveNoteButton.setOnClickListener {
            val name = noteNameEditText.text.toString()
            val content = noteEditText.text.toString()
            if (name.isNotBlank() && content.isNotBlank()) {
                if (noteId != -1) {
                    repository.saveNotes(repository.loadNotes().map {
                        if (it.id == noteId) it.copy(name = name, content = content) else it
                    })
                } else {
                    val newNote = Note(repository.loadNotes().size + 1, name, content)
                    repository.saveNotes(repository.loadNotes() + newNote)
                }
                finish()
            }
        }

        deleteNoteButton.setOnClickListener {
            if (noteId != -1) {
                repository.deleteNote(noteId!!)
                finish()
            }
        }

        cancelNoteButton.setOnClickListener {
            finish()
        }
    }
}

