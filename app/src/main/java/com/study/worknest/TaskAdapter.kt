package com.study.worknest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: MutableList<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val taskDeadline: TextView = itemView.findViewById(R.id.taskDeadline)
        val statusLayout: LinearLayout = itemView.findViewById(R.id.statusLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val inflater = LayoutInflater.from(holder.itemView.context)
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.taskDescription.text = task.description
        holder.taskDeadline.text = "Deadline: " + task.deadline.toString()
        when(task.priority){
            "High" -> {
                val priorityView = inflater.inflate(R.layout.high_priority, holder.statusLayout, false)
                holder.statusLayout.addView(priorityView)
            }
            "Medium" -> {
                val priorityView = inflater.inflate(R.layout.medium_priority, holder.statusLayout, false)
                holder.statusLayout.addView(priorityView)
            }
            "Low" -> {
                val priorityView = inflater.inflate(R.layout.low_priority, holder.statusLayout, false)
                holder.statusLayout.addView(priorityView)
            }
        }
        when(task.status){
            "To Do" -> {
                val statusView = inflater.inflate(R.layout.status_to_do, holder.statusLayout, false)
                holder.statusLayout.addView(statusView)
            }
            "In Progress" -> {
                val statusView = inflater.inflate(R.layout.status_in_progress, holder.statusLayout, false)
                holder.statusLayout.addView(statusView)
            }
            "Done" -> {
                val statusView = inflater.inflate(R.layout.status_done, holder.statusLayout, false)
                holder.statusLayout.addView(statusView)
            }
        }
    }

    override fun getItemCount(): Int = tasks.size
}