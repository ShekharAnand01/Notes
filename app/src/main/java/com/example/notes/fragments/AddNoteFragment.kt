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
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.databinding.FragmentAddNoteBinding
import com.example.notes.model.Notes
import com.example.notes.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddNoteFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var notesView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel = (activity as MainActivity).viewModel
        notesView = view

    }

    private fun saveNote(view: View) {
        val title = binding.addNoteTitle.text.toString().trim()
        val desc = binding.addNoteDesc.text.toString().trim()
        if(title.isNotEmpty()){
            val note = Notes(0, title, desc,getCurrentTime())
            viewModel.insertNote(note)

            Toast.makeText(notesView.context, "Note Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        }
        else{
            Toast.makeText(notesView.context, "Please enter the title", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.men_add_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveNote(notesView)
                true
            }
            else -> false
        }
    }

    private fun getCurrentTime(): String {
        val now = Date()

        val sdf = SimpleDateFormat("dd-MM-yyyy | HH:mm", Locale.getDefault())

        return sdf.format(now)
    }


}