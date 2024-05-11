package com.example.simplenotes

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Repository(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveNotes(notes: List<Note>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(notes)
        editor.putString("notes", json)
        editor.apply()
    }

    fun loadNotes(): List<Note> {
        val json = sharedPreferences.getString("notes", null)
        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun deleteNote(noteId: Int) {
        val notes = loadNotes().toMutableList()
        notes.removeAll { it.id == noteId }
        saveNotes(notes)
    }
}