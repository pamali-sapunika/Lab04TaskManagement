package com.example.thetaskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thetaskapp.databinding.TaskLayoutBinding
import com.example.thetaskapp.fragments.HomeFragmentDirections
import com.example.thetaskapp.model.Task

//RecycleView subclass TaskAdapter
class TaskAdapter :RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() { //providing views that represent items in a data set

    //holds references to the views in the Recycler View
    class TaskViewHolder(val itemBinding: TaskLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)

    //check if the content are same in tasks
    private val differentCallback = object :DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.taskDesc ==newItem.taskDesc &&
                    oldItem.taskTitle == newItem.taskTitle
//                    oldItem.taskTime == newItem.taskTime

        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }
    //updating RecyclerView with updatings
    val differ = AsyncListDiffer(this,differentCallback)

    //Needs a new ViewHolder for a new task - RecycleView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
       return TaskViewHolder(
           //Inflates layout for the item
           TaskLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {//bind data to a ViewHolder in a specific position
       val currentTask = differ.currentList[position]
        holder.itemBinding.noteTitle.text = currentTask.taskTitle
        holder.itemBinding.noteDesc.text = currentTask.taskDesc
//        holder.itemBinding.noteTime.text = currentTask.taskTime

        //go to editTask fragment
        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToEditTaskFragment(currentTask)
            it.findNavController().navigate(direction)
        }


    }
}