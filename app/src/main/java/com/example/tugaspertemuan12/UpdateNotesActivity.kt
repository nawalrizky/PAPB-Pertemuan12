package com.example.tugaspertemuan12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugaspertemuan12.databinding.ActivityUpdateNotesBinding
import com.google.firebase.firestore.FirebaseFirestore

class UpdateNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNotesBinding
    private val db = FirebaseFirestore.getInstance()
    private var noteId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getStringExtra("note_id") ?: ""
        if (noteId == "") {
            finish()
            return
        }

        db.collection("notes").document(noteId).get()
            .addOnSuccessListener { document ->
                val note = document.toObject(Note::class.java)
                binding.updateNoteTitleEditText.setText(note?.notesName)
                binding.updateNoteContentEditText.setText(note?.notesContent)
            }
            .addOnFailureListener {
                // Handle the failure to fetch the note
                Toast.makeText(this, "Failed to fetch note", Toast.LENGTH_SHORT).show()
                finish()
            }

        binding.updateButton.setOnClickListener {
            val newTitle = binding.updateNoteTitleEditText.text.toString()
            val newContent = binding.updateNoteContentEditText.text.toString()
            val updatedNote = Note(noteId, newTitle, newContent)

            db.collection("notes").document(noteId).set(updatedNote)
                .addOnSuccessListener {
                    finish()
                    Toast.makeText(this, "Catatan Diperbarui", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal memperbarui catatan", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
