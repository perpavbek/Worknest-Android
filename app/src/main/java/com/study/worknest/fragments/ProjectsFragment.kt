package com.study.worknest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.API.services.ProjectService
import com.study.worknest.R
import com.study.worknest.adapters.ProjectAdapter

class ProjectsFragment: Fragment() {
    private lateinit var projectAdapter: ProjectAdapter
    private lateinit var projectList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectList = view.findViewById(R.id.projectList)
        projectList.layoutManager = LinearLayoutManager(requireContext())
        projectList.isNestedScrollingEnabled = false

        ProjectService.fetchProjects(requireContext()) { fetchedProjects ->
            if(isAdded && context != null){
                if (!fetchedProjects.isNullOrEmpty()) {
                    projectAdapter = ProjectAdapter(fetchedProjects)
                    projectList.adapter = projectAdapter
                } else {
                    Toast.makeText(requireContext(), "You don't have tasks", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}