package com.study.worknest.fragments.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.study.worknest.API.services.ProjectService
import com.study.worknest.R
import com.study.worknest.data.Project
import com.study.worknest.data.requests.ProjectRequest

class CreateProjectDialogFragment : DialogFragment() {

    private lateinit var createBtn: Button
    private lateinit var etName: EditText
    private lateinit var etDescription: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_fragment_create_project, container, false)

        createBtn = view.findViewById(R.id.btn_create)
        etName = view.findViewById(R.id.et_name)
        etDescription = view.findViewById(R.id.et_description)

        createBtn.setOnClickListener {
            if(etName.text.isNotEmpty() && etDescription.text.isNotEmpty()){
                var project = ProjectRequest.Builder()
                project.name(etName.text.toString())
                project.description(etDescription.text.toString())
                ProjectService.createProject(requireContext(), project.build()){ isCreated ->
                    if(isCreated == true){
                        dismiss()
                    }
                    else{
                        Toast.makeText(requireContext(), "Project creation error. Try Later", Toast.LENGTH_SHORT).show()
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

    companion object {
        fun newInstance(): CreateProjectDialogFragment {
            return CreateProjectDialogFragment()
        }
    }
}