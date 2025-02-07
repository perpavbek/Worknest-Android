package com.study.worknest.fragments.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.API.services.ProjectService
import com.study.worknest.API.services.TeamService
import com.study.worknest.API.services.UserService
import com.study.worknest.R
import com.study.worknest.data.Project
import com.study.worknest.data.Team
import com.study.worknest.data.User
import com.study.worknest.data.requests.TeamRequest

class CreateTeamDialogFragment : DialogFragment() {

    private lateinit var createBtn: Button
    private lateinit var etName: EditText
    private lateinit var actwProject: AutoCompleteTextView
    private lateinit var actwLead: AutoCompleteTextView
    private lateinit var actwMember: AutoCompleteTextView
    private lateinit var teamMembers: RecyclerView
    private var team: TeamRequest.Builder = TeamRequest.Builder()
    private var projects: MutableList<Project> = mutableListOf()
    private var projectNames: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_fragment_create_team, container, false)

        createBtn = view.findViewById(R.id.btn_create)
        etName = view.findViewById(R.id.et_name)
        actwProject = view.findViewById(R.id.actw_project)
        actwLead = view.findViewById(R.id.actw_lead)
        actwMember = view.findViewById(R.id.actw_member)
        teamMembers = view.findViewById(R.id.team_members)

        actwLead.threshold = 1
        actwProject.threshold = 0

        ProjectService.fetchProjects(requireContext()){ fetchedProjects ->
            if(fetchedProjects != null){
                projects = fetchedProjects
                projectNames = fetchedProjects.map { it.name!! }
                val projectAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, projectNames)
                actwProject.setAdapter(projectAdapter)
            }
        }
        etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    team.name(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        actwLead.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    UserService.searchUser(requireContext(), s.toString()){ users ->
                        var userNames: List<String> = listOf()
                        if(users.isNotEmpty()){
                            userNames = users.map { it.username!! }
                        }
                        var usersAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, userNames)
                        actwLead.setAdapter(usersAdapter)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        actwMember.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    UserService.searchUser(requireContext(), s.toString()){ users ->
                        var userNames: List<String> = listOf()
                        if(users.isNotEmpty()){
                            userNames = users.map { it.username!! }
                        }
                        var usersAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, userNames)
                        actwMember.setAdapter(usersAdapter)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        actwLead.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position) as String
            UserService.getUserByUsername(requireContext(), selectedName){ user ->
                if(user != null){
                    if(user.userId != null){
                        team.lead(user.userId!!)
                        team.addMember(user.userId!!)
                    }
                }
            }
        }
        actwMember.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position) as String
            UserService.getUserByUsername(requireContext(), selectedName){ user ->
                if(user != null){
                    if(user.userId != null){
                        team.addMember(user.userId!!)
                    }
                }
            }
        }
        actwProject.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position) as String
            val selectedProject = projects.find { it.name == selectedName }
            if (selectedProject != null){
                team.projectId(selectedProject.projectId)
            }
        }
        createBtn.setOnClickListener {
            if(team.isFilled()){
                TeamService.createTeam(team.build(), requireContext()){ isCreated ->
                    if(isCreated){
                        dismiss()
                    }
                    else{
                        Toast.makeText(requireContext(), "Team Creating Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(requireContext(), "Fill All Labels", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.DialogAnimation)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    companion object {
        fun newInstance(): CreateTeamDialogFragment {
            return CreateTeamDialogFragment()
        }
    }
}