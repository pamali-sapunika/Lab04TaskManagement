package com.example.thetaskapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.thetaskapp.model.Task
import com.example.thetaskapp.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(app:Application,private val taskRepository: TaskRepository):AndroidViewModel(app) {
    fun addTask(task: Task) =
       viewModelScope.launch {
           taskRepository.insertTask(task)
       }

    fun deleteTask(task: Task) =
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }

    fun updateTask(task: Task) =
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    fun getAllTasks() = taskRepository.getAllTasks()

    fun searchTask(query: String?) =
        taskRepository.searchTask(query)
}