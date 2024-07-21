package com.example.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.databinding.FragmentEditNoteBinding
import com.example.notes.fragments.EditNoteFragmentArgs
import com.example.notes.model.Notes
import com.example.notes.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditNoteFragment : Fragment(),MenuProvider {

    private lateinit var binding: FragmentEditNoteBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var currentNote: Notes
    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel = (activity as MainActivity).viewModel

        currentNote=args.note!!
        binding.editNoteTitle.setText(currentNote.title)
        binding.editNoteDesc.setText(currentNote.description)

        binding.editNoteFab.setOnClickListener {
            val title = binding.editNoteTitle.text.toString().trim()
            val desc = binding.editNoteDesc.text.toString().trim()

            if(title.isNotEmpty()){
                val note = Notes(currentNote.id, title, desc,getCurrentTime())
                viewModel.updateNote(note)
                Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }
            else{
                Toast.makeText(context, "Please enter the title", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun deleteNote() {
        android.app.AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Delete") { _, _ ->
                viewModel.deleteNote(currentNote)
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)

            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }

    private fun getCurrentTime(): String {
        val now = Date()

        val sdf = SimpleDateFormat("dd-MM | HH:mm", Locale.getDefault())

        return sdf.format(now)
    }

}