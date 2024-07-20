package com.example.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.adapter.NotesAdapter
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.model.Notes
import com.example.notes.viewmodel.NotesViewModel


class HomeFragment : Fragment(), SearchView.OnQueryTextListener, MenuProvider {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var noteAdapter: NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost:MenuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        viewModel=(activity as MainActivity).viewModel

        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }

        setupRecyclerView()
    }

    private fun updateUI(note: List<Notes>?){
        if(note?.isNotEmpty() == true){
         binding.homeRecyclerView.visibility=View.VISIBLE
            binding.emptyNotesImage.visibility=View.GONE
        }
        else{
            binding.homeRecyclerView.visibility=View.GONE
            binding.emptyNotesImage.visibility=View.VISIBLE
        }
    }

    private fun setupRecyclerView(){
        noteAdapter=NotesAdapter()
        binding.homeRecyclerView.apply {
            layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter=noteAdapter
        }

        viewModel.getAllNotes().observe(viewLifecycleOwner){note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)


        }
    }

    private fun searchNote(query: String?){
        val searchQuery = "%$query%"
        viewModel.searchNote(searchQuery).observe(this) { list ->
            noteAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote(newText)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)

        val searchView=menu.findItem(R.id.searchMenu).actionView as SearchView
        searchView.isSubmitButtonEnabled=false
        searchView.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}