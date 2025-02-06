package com.study.worknest.fragments.dialogs

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.study.worknest.API.services.ProjectService
import com.study.worknest.API.services.TaskService
import com.study.worknest.API.services.TeamService
import com.study.worknest.API.services.UserService
import com.study.worknest.R
import com.study.worknest.data.Project
import com.study.worknest.data.Task
import com.study.worknest.data.Team
import com.study.worknest.data.User
import com.study.worknest.data.requests.TaskRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CreateTaskDialogFragment : DialogFragment() {

    private lateinit var createBtn: Button
    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var spProject: Spinner
    private lateinit var spTeam: Spinner
    private lateinit var spUser: Spinner
    private lateinit var spPriority: Spinner
    private lateinit var spStatus: Spinner
    private lateinit var twDeadline: TextView
    private var projects: MutableList<Project> = mutableListOf()
    private var projectNames: List<String> = listOf()
    private var teams: MutableList<Team> = mutableListOf()
    private var teamNames: List<String> = listOf()
    private var teamMembers: MutableList<User> = mutableListOf()
    private var teamMemberNames: List<String> = listOf()
    private var task: TaskRequest.Builder = TaskRequest.Builder()
    private var priorities = arrayOf("Low", "Medium", "High")
    private var statuses = arrayOf("To Do", "In Progress", "Completed")
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_fragment_create_task, container, false)

        createBtn = view.findViewById(R.id.btn_create)
        etName = view.findViewById(R.id.et_name)
        etDescription = view.findViewById(R.id.et_description)
        spProject = view.findViewById(R.id.sp_project)
        spTeam = view.findViewById(R.id.sp_team)
        spUser = view.findViewById(R.id.sp_user)
        spPriority = view.findViewById(R.id.sp_priority)
        spStatus = view.findViewById(R.id.sp_status)
        twDeadline = view.findViewById(R.id.tw_deadline)

        val priorityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, priorities)
        spPriority.adapter = priorityAdapter

        val statusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, statuses)
        spStatus.adapter = statusAdapter

        ProjectService.fetchProjects(requireContext()){ fetchedProjects ->
            if(fetchedProjects != null){
                projects = fetchedProjects
                projectNames = fetchedProjects.map { it.name!! }
                val projectAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, projectNames)
                spProject.setAdapter(projectAdapter)
            }
        }

        spProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedName = projectNames[position]
                val selectedProject = projects.find { it.name == selectedName }
                if(selectedProject != null){
                    if(selectedProject.projectId != null){
                        task.projectId(selectedProject.projectId)

                        TeamService.getTeamsByProjectId(requireContext(), selectedProject.projectId!!){ fetchedTeams ->
                            if(fetchedTeams != null){
                                teams = fetchedTeams
                                teamNames = fetchedTeams.map { it.name!! }
                                val teamAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, teamNames)
                                spTeam.setAdapter(teamAdapter)
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedName = teamNames[position]
                val selectedTeam = teams.find { it.name == selectedName }
                if(selectedTeam != null){
                    if(selectedTeam.teamId != null){
                        task.teamId(selectedTeam.teamId)

                        UserService.getUsersByTeamId(requireContext(), selectedTeam.teamId!!){ fetchedUsers ->
                            if(fetchedUsers != null){
                                teamMembers = fetchedUsers
                                teamMemberNames = fetchedUsers.map { it.username!! }
                                val userAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, teamMemberNames)
                                spUser.setAdapter(userAdapter)
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedName = teamMemberNames[position]
                val selectedUser = teamMembers.find { it.username == selectedName }
                if(selectedUser != null){
                    if(selectedUser.userId != null){
                        task.assignedTo(selectedUser.userId)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                task.priority(priorities[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                task.status(statuses[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    task.name(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        etDescription.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    task.description(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        twDeadline.setOnClickListener {
            showDatePickerDialog()
        }

        createBtn.setOnClickListener {
            if(task.isFilled()){
                TaskService.createTask(task.build(), requireContext()){ isCreated ->
                    if(isCreated){
                        dismiss()
                    }
                    else{
                        Toast.makeText(requireContext(), "Task Creation Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(requireContext(), "Fill all labels", Toast.LENGTH_SHORT).show()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {
        val today = LocalDate.now()

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)

            twDeadline.text = selectedDate!!.format(dateFormatter)

            task.deadline(selectedDate)

        }, today.year, today.monthValue - 1, today.dayOfMonth)

        datePickerDialog.show()
    }

    companion object {
        fun newInstance(): CreateTaskDialogFragment {
            return CreateTaskDialogFragment()
        }
    }
}