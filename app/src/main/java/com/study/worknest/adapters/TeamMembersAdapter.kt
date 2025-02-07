package com.study.worknest.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.R
import com.study.worknest.data.User

class TeamMembersAdapter (
    private var teamMembers: List<User>?,
) : RecyclerView.Adapter<TeamMembersAdapter.MemberViewHolder>() {

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val twUsername: TextView = itemView.findViewById(R.id.tw_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_member_item, parent, false)
        return MemberViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = teamMembers?.get(position)
        holder.twUsername.text = member?.username
    }

    override fun getItemCount(): Int {
        if(teamMembers != null) {
            return teamMembers!!.size
        }
        else{
            return 0
        }
    }
}