package com.example.thetaskapp.fragments

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
import com.example.thetaskapp.MainActivity
import com.example.thetaskapp.R
import com.example.thetaskapp.adapter.TaskAdapter
import com.example.thetaskapp.databinding.FragmentHomeBinding
import com.example.thetaskapp.model.Task
import com.example.thetaskapp.viewmodel.TaskViewModel


class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener,MenuProvider {

    private var homeBinding:FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter


    //layout for homefragemnt
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost:MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        //Initializes taskViewModel by retrieving it from the MainActivity.
        taskViewModel = (activity as MainActivity).taskViewModel

        //call  setupHomeRecycleView() to configure RecycleView
        setupHomeRecycleView()

        binding.addNoteFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addTaskfragment)
        }
    }

   private fun updateUI(task:List<Task>?){ //updates UI frontend
       if (task!=null){
           if(task.isNotEmpty()){
               binding.emptyNotesImage.visibility = View.GONE
               binding.homeRecyclerView.visibility = View.VISIBLE
           }else{
               binding.emptyNotesImage.visibility = View.VISIBLE
               binding.homeRecyclerView.visibility =View.GONE
           }
       }
   }

   private fun setupHomeRecycleView(){  //to configure RecycleView

       //initiate TaskAdapter with new instance
       taskAdapter = TaskAdapter()
       binding.homeRecyclerView.apply {
           layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
           setHasFixedSize(true)
           adapter = taskAdapter  //updates the adapter
       }

       activity?.let {
           taskViewModel.getAllTasks().observe(viewLifecycleOwner){task ->  //getAll Tasks list from viewModel
               taskAdapter.differ.submitList(task)
               updateUI(task) //call UpdateUI()
           }
       }
   }
    private fun searchTask(query: String?){
        val searchQuery = "%$query"

        taskViewModel.searchTask(searchQuery).observe(this){list ->
            taskAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!= null){
            searchTask(newText)
        }
        return true
    }

    override fun onDestroy() {  //To avoid memory leaks destroy homebinding
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu) //inflates menu to homescreen

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }


}