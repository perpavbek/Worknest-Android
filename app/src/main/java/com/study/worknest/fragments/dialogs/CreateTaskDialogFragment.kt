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

class CreateTaskDialogFragment : DialogFragment() {

    private lateinit var createBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_fragment_create_task, container, false)

        createBtn = view.findViewById(R.id.btn_create)

        createBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Task pressed", Toast.LENGTH_SHORT).show()
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
        fun newInstance(): CreateTaskDialogFragment {
            return CreateTaskDialogFragment()
        }
    }
}