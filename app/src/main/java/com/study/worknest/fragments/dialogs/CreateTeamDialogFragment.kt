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

    private var team: Team? = null
    private var teamRequest: TeamRequest.Builder = TeamRequest.Builder()
    private var projects: MutableList<Project> = mutableListOf()

    companion object {
        private const val ARG_TEAM = "arg_team"

        fun newInstance(team: Team? = null): CreateTeamDialogFragment {
            val fragment = CreateTeamDialogFragment()
            val args = Bundle()
            if (team != null) {
                args.putParcelable(ARG_TEAM, team)
            }
            fragment.arguments = args
            return fragment
        }
    }

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

        // Загружаем проекты для выбора
        ProjectService.fetchProjects(requireContext()) { fetchedProjects ->
            fetchedProjects?.let {
                projects = it.toMutableList()
                val projectNames = it.mapNotNull { project -> project.name }
                actwProject.setAdapter(
                    ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, projectNames)
                )
            }
        }

        // Проверяем, передана ли команда
        team = arguments?.getParcelable(ARG_TEAM)
        team?.let { setTeamData(it) }

        etName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                teamRequest.name(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        actwLead.addTextChangedListener(createUserSearchWatcher(actwLead))
        actwMember.addTextChangedListener(createUserSearchWatcher(actwMember))

        actwLead.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position) as String
            setUserAsLead(selectedName)
        }

        actwMember.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position) as String
            addUserToTeam(selectedName)
        }

        actwProject.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position) as String
            projects.find { it.name == selectedName }?.let {
                teamRequest.projectId(it.projectId)
            }
        }

        createBtn.setOnClickListener {
            if (teamRequest.isFilled()) {
                if (team == null) {
                    createTeam()
                } else {
                    updateTeam()
                }
            } else {
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

    private fun setTeamData(team: Team) {
        etName.setText(team.name)
        actwProject.setText(team.project?.name ?: "")
        actwLead.setText(team.lead?.username ?: "")

        team.members?.forEach { member ->
            actwMember.setText(member.username ?: "", false)
        }

        teamRequest.apply {
            name(team.name)
            lead(team.lead?.userId ?: "")
            projectId(team.project?.projectId ?: "")
            team.members?.mapNotNull { it.userId }?.forEach { addMember(it) }
        }

        createBtn.text = "Update"
    }

    private fun createTeam() {
        TeamService.createTeam(teamRequest.build(), requireContext()) { isCreated ->
            if (isCreated) {
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Error creating team", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTeam() {
        team?.teamId?.let { teamId ->
            TeamService.updateTeam(teamId, teamRequest.build(), requireContext()) { isUpdated ->
                if (isUpdated) {
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Error updating team", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUserAsLead(username: String) {
        UserService.getUserByUsername(requireContext(), username) { user ->
            user?.userId?.let {
                teamRequest.lead(it)
                teamRequest.addMember(it)
            }
        }
    }

    private fun addUserToTeam(username: String) {
        UserService.getUserByUsername(requireContext(), username) { user ->
            user?.userId?.let { teamRequest.addMember(it) }
        }
    }

    private fun createUserSearchWatcher(autoCompleteTextView: AutoCompleteTextView): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    UserService.searchUser(requireContext(), s.toString()) { users ->
                        val userNames = users.mapNotNull { it.username }
                        autoCompleteTextView.setAdapter(
                            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, userNames)
                        )
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        }
    }
}
