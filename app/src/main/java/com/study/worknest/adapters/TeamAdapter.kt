package com.study.worknest.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.R
import com.study.worknest.data.Project
import com.study.worknest.data.Team

class TeamAdapter(
    private var teams: List<Team>?,
    private var teamsMap: MutableMap<Team, Project>?
) : RecyclerView.Adapter<TeamAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName: TextView = itemView.findViewById(R.id.teamName)
        val projectName: TextView = itemView.findViewById(R.id.projectName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        return ProjectViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val team = teams?.get(position)
        holder.teamName.text = team?.name
        holder.projectName.text = teamsMap?.get(team)?.name
    }

    override fun getItemCount(): Int {
        if(teams != null) {
            return teams!!.size
        }
        else{
            return 0
        }
    }
}