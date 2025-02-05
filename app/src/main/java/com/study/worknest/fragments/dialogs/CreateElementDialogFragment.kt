package com.study.worknest.fragments.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.study.worknest.R

class CreateElementDialogFragment : DialogFragment() {

    private lateinit var addProject: Button
    private lateinit var addTeam: Button
    private lateinit var addTask: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_fragment_create_element, container, false)

        addProject = view.findViewById(R.id.add_project)
        addTeam = view.findViewById(R.id.add_team)
        addTask = view.findViewById(R.id.add_task)

        addProject.setOnClickListener {
            dismiss()
            val projectDialog = CreateProjectDialogFragment.newInstance()
            projectDialog.show(parentFragmentManager, "CreateProjectDialog")
        }
        addTeam.setOnClickListener {
            dismiss()
            val teamDialog = CreateTeamDialogFragment.newInstance()
            teamDialog.show(parentFragmentManager, "CreateTeamDialog")
        }
        addTask.setOnClickListener {
            dismiss()
            val teamDialog = CreateTaskDialogFragment.newInstance()
            teamDialog.show(parentFragmentManager, "CreateTaskDialog")
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
        fun newInstance(): CreateElementDialogFragment {
            return CreateElementDialogFragment()
        }
    }
}