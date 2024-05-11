package com.example.simplenotes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository(this)
        notesRecyclerView = findViewById(R.id.notesRecyclerView)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        noteAdapter = NoteAdapter(repository.loadNotes(),
            onClick = { note -> // Обработка нажатия
                val intent = Intent(this, NoteActivity::class.java)
                intent.putExtra("noteId", note.id)
                startActivity(intent)
            },
            onLongClick = { note -> // Обработка долгого нажатия (пример удаления)
                // Можно добавить диалоговое окно для подтверждения удаления
                repository.deleteNote(note.id)
                noteAdapter.updateNotes(repository.loadNotes())
            }
        )

        notesRecyclerView.adapter = noteAdapter
        val addNoteButton: FloatingActionButton = findViewById(R.id.addNoteButton)
        addNoteButton.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }
    }
    override fun onResume() {
        super.onResume()
        // Обновление списка заметок
        val repository = Repository(this)
        noteAdapter.updateNotes(repository.loadNotes())
    }

}
