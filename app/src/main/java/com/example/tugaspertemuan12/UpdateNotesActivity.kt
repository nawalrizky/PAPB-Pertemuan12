package com.example.tugaspertemuan12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugaspertemuan12.databinding.ActivityUpdateNotesBinding

class UpdateNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNotesBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateNoteTitleEditText.setText(note.notesName)
        binding.updateNoteContentEditText.setText(note.notesContent)

        binding.updateButton.setOnClickListener{
            val newTitle = binding.updateNoteTitleEditText.text.toString()
            val newContent = binding.updateNoteContentEditText.text.toString()
            val updatedNote = Note(noteId, newTitle, newContent)
            val success = db.updateNote(updatedNote)
            if (success) {
                finish()
                Toast.makeText(this, "Catatan Diperbarui", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal memperbarui catatan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
