package com.example.tugaspertemuan12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tugaspertemuan12.databinding.ActivityAddNotesBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            val noteTitle = binding.noteTitleEditText.text.toString()
            val noteContent = binding.noteContentEditText.text.toString()

            if (noteTitle.isNotEmpty() && noteContent.isNotEmpty()) {
                val note = Note(UUID.randomUUID().toString(), noteTitle, noteContent)
                db.collection("notes").document(note.id).set(note)
                    .addOnSuccessListener {
                        finish()
                        Toast.makeText(this, "Catatan Disimpan", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan catatan", Toast.LENGTH_SHORT).show()
                        Log.d("AddNotesActivity", "Error: ${it.message}")
                    }
            } else {
                Toast.makeText(this, "Judul dan isi catatan harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
