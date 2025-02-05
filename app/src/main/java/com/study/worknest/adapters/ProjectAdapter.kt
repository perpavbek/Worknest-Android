package com.study.worknest.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.R
import com.study.worknest.data.Project

class ProjectAdapter(
    private var projects: MutableList<Project>?
) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectName: TextView = itemView.findViewById(R.id.projectName)
        val projectDescription: TextView = itemView.findViewById(R.id.projectDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ProjectViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projects?.get(position)
        holder.projectName.text = project?.name
        holder.projectDescription.text = project?.description
    }

    override fun getItemCount(): Int {
        if(projects != null) {
            return projects!!.size
        }
        else{
            return 0
        }
    }
}