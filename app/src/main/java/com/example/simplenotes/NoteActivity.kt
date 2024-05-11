package com.example.simplenotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NoteActivity : AppCompatActivity() {

    private lateinit var noteEditText: EditText
    private lateinit var saveNoteButton: Button
    private lateinit var cancelNoteButton: Button
    private var noteId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        noteEditText = findViewById(R.id.noteEditText)
        saveNoteButton = findViewById(R.id.saveNoteButton)
        cancelNoteButton = findViewById(R.id.cancelNoteButton)
        val repository = Repository(this)

        noteId = intent?.getIntExtra("noteId", -1)
        if (noteId != null && noteId != -1) {
            val note = repository.loadNotes().find { it.id == noteId }
            note?.let {
                noteEditText.setText(it.content)
            }
        }

        saveNoteButton.setOnClickListener {
            val content = noteEditText.text.toString()
            if (content.isNotBlank()) {
                if (noteId != null && noteId != -1) {
                    repository.saveNotes(repository.loadNotes().map {
                        if (it.id == noteId) it.copy(content = content) else it
                    })
                } else {
                    val newNote = Note(repository.loadNotes().size + 1, content)
                    repository.saveNotes(repository.loadNotes() + newNote)
                }
                finish()
            }
        }

        cancelNoteButton.setOnClickListener {
            finish()
        }
    }
}