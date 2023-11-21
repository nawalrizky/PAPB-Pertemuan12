package com.example.tugaspertemuan12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugaspertemuan12.databinding.ActivityAddNotesBinding

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            val noteTitle = binding.noteTitleEditText.text.toString()
            val noteContent = binding.noteContentEditText.text.toString()

            if (noteTitle.isNotEmpty() && noteContent.isNotEmpty()) {
                val note = Note(0, noteTitle, noteContent)
                val success = db.insertNote(note)
                if (success) {
                    finish()
                    Toast.makeText(this, "Catatan Disimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Gagal menyimpan catatan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Judul dan isi catatan harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
