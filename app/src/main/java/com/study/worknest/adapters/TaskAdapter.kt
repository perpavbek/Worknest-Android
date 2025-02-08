package com.study.worknest.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.API.services.TaskService
import com.study.worknest.R
import com.study.worknest.data.Task
import com.study.worknest.fragments.HomeFragment

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val onTaskUpdated: () -> Unit
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val inflater = LayoutInflater.from(holder.itemView.context)
        val task = tasks?.get(position)
        holder.taskName.text = task?.name
        holder.taskDescription.text = task?.description
        holder.taskDeadline.text = "Deadline: " + task?.deadline

        holder.statusLayout.removeAllViews()

        val priorityView = when (task?.priority) {
            "High" -> inflater.inflate(R.layout.high_priority, holder.statusLayout, false)
            "Medium" -> inflater.inflate(R.layout.medium_priority, holder.statusLayout, false)
            "Low" -> inflater.inflate(R.layout.low_priority, holder.statusLayout, false)
            else -> null
        }
        priorityView?.let {
            holder.statusLayout.addView(it)
            it.setOnClickListener {
                showPriorityDialog(holder.itemView.context, task!!, position)
            }
        }

        val statusView = when (task?.status) {
            "To Do" -> inflater.inflate(R.layout.status_to_do, holder.statusLayout, false)
            "In Progress" -> inflater.inflate(R.layout.status_in_progress, holder.statusLayout, false)
            "Completed" -> inflater.inflate(R.layout.status_completed, holder.statusLayout, false)
            else -> null
        }
        statusView?.let {
            holder.statusLayout.addView(it)
            it.setOnClickListener {
                showStatusDialog(holder.itemView.context, task!!, position)
            }
        }
    }

    private fun showPriorityDialog(context: Context, task: Task, position: Int) {
        val priorities = arrayOf("High", "Medium", "Low")
        val currentPriorityIndex = priorities.indexOf(task.priority)

        AlertDialog.Builder(context)
            .setTitle("Choose Priority")
            .setSingleChoiceItems(priorities, currentPriorityIndex) { dialog, which ->
                val newPriority = priorities[which]
                if (task.priority != newPriority) {
                    task.priority = newPriority
                    updateTaskOnServer(context, task) { isUpdated ->
                        if (isUpdated) {
                            onTaskUpdated()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(context, "Failed to update priority", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showStatusDialog(context: Context, task: Task, position: Int) {
        val statuses = arrayOf("To Do", "In Progress", "Completed")
        val currentPriorityIndex = statuses.indexOf(task.status)

        AlertDialog.Builder(context)
            .setTitle("Choose Status")
            .setSingleChoiceItems(statuses, currentPriorityIndex) { dialog, which ->
                val newStatus = statuses[which]
                if (task.status != newStatus) {
                    task.status = newStatus
                    updateTaskOnServer(context, task) { isUpdated ->
                        if (isUpdated) {
                            onTaskUpdated()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateTaskOnServer(context: Context, task: Task, callback: (Boolean) -> Unit) {
        TaskService.updateTaskById(task, task.taskId!!, context) { isUpdated ->
            callback(isUpdated)
        }
    }

    override fun getItemCount(): Int {
        if(tasks != null) {
            return tasks!!.size
        }
        else{
            return 0
        }
    }

}