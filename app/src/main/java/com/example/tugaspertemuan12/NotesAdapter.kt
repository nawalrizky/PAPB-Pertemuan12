package com.example.tugaspertemuan12

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspertemuan12.R

class NotesAdapter(private var notes: MutableList<Note>, private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitleTextView: TextView = itemView.findViewById(R.id.noteTitleTextView)
        val noteContentTextView: TextView = itemView.findViewById(R.id.noteContentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.noteTitleTextView.text = note.notesName
        holder.noteContentTextView.text = note.notesContent

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNotesActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Delete Note")
                setMessage("Are you sure you want to delete this note?")
                setPositiveButton("Yes") { _, _ ->
                    db.deleteNote(note.id)
                    notes.removeAt(position)
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("No", null)
            }.create().show()
        }
    }
}
