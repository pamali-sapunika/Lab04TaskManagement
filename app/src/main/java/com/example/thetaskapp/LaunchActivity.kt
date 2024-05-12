package com.example.thetaskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.thetaskapp.database.TaskDatabase
import com.example.thetaskapp.repository.TaskRepository
import com.example.thetaskapp.viewmodel.TaskViewModel
import com.example.thetaskapp.viewmodel.TaskViewModelFactory

class LaunchActivity : AppCompatActivity() {

//    lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

//        setupViewModel()

    }

//    private fun setupViewModel(){
//        val taskRepository = TaskRepository(TaskDatabase(this))
//        val viewModelProviderFactory = TaskViewModelFactory(application,taskRepository)
//        taskViewModel = ViewModelProvider(this,viewModelProviderFactory)[TaskViewModel::class.java]
//    }
}