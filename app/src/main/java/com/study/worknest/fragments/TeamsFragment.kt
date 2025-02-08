package com.study.worknest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.API.services.ProjectService
import com.study.worknest.API.services.TeamService
import com.study.worknest.R
import com.study.worknest.adapters.ProjectAdapter
import com.study.worknest.adapters.TeamAdapter
import com.study.worknest.data.Project
import com.study.worknest.data.Team

class TeamsFragment: Fragment() {
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var teamList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamList = view.findViewById(R.id.teamList)
        teamList.layoutManager = LinearLayoutManager(requireContext())
        teamList.isNestedScrollingEnabled = false
        TeamService.fetchTeams(requireContext()) { fetchedTeams ->
            if(isAdded && context != null){
                var teamsMap: MutableMap<Team, Project>? = mutableMapOf()
                if (!fetchedTeams.isNullOrEmpty()) {
                    fetchedTeams.forEach { team ->
                        ProjectService.getProjectById(requireContext(), team.projectId!!) { project ->
                            teamsMap?.set(team, project!!)
                            teamAdapter = TeamAdapter(teamsMap?.keys?.toList(), teamsMap)
                            teamList.adapter = teamAdapter
                            val animation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_fade_in)
                            teamList.layoutAnimation = animation
                            teamList.scheduleLayoutAnimation()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "You don't have teams", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}