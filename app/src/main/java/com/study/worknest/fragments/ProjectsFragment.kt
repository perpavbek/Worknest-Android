package com.study.worknest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.worknest.API.services.ProjectService
import com.study.worknest.API.services.TaskService
import com.study.worknest.R
import com.study.worknest.adapters.TaskAdapter

class ProjectsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProjectService.fetchProjects(requireContext()) { fetchedProjects ->
            if(isAdded && context != null){
                if (!fetchedProjects.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), fetchedProjects[0].name, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "You don't have tasks", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}