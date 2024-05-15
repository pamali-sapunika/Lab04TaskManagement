package com.example.thetaskapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thetaskapp.repository.TaskRepository

class TaskViewModelFactory(val app:Application,private val taskRepository: TaskRepository):ViewModelProvider.Factory {

    //instantiating ViewModel classes.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {  //modelclass -> parameter
        return TaskViewModel(app,taskRepository)as T  //passes Application and Repositery instances
    }
}