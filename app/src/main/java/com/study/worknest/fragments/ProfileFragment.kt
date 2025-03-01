package com.study.worknest.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.study.worknest.API.APIService
import com.study.worknest.API.services.UserService
import com.study.worknest.R
import com.study.worknest.activities.AuthActivity
import com.study.worknest.utils.SharedPreferencesManager

class ProfileFragment: Fragment() {
    private var userId: Int? = null
    private lateinit var twUsername: TextView
    private lateinit var etUsername: TextView
    private lateinit var etEmail: TextView
    private lateinit var etPhoneNumber: TextView
    private lateinit var logOutButton: Button;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = SharedPreferencesManager.getInstance(requireContext()).getData("USER_ID")?.toInt()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        twUsername = view.findViewById(R.id.tw_username)
        etUsername = view.findViewById((R.id.et_username))
        etEmail = view.findViewById((R.id.et_email))
        etPhoneNumber = view.findViewById((R.id.et_phone_number))
        logOutButton = view.findViewById(R.id.log_out_button)

        userId?.let { id ->
            UserService.getUserById(requireContext(), id) { user ->
                if (isAdded && context != null) {
                    user?.let {
                        twUsername.text = it.username
                        etUsername.setText(it.username)
                        etEmail.setText(it.email)
                        etPhoneNumber.setText(it.phoneNumber)
                    } ?: run {
                        Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        logOutButton.setOnClickListener{
            SharedPreferencesManager.getInstance(requireContext()).clearData()
            APIService.getInstance(requireContext())?.getCookieJar()?.clearCookies()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            activity?.finish()
        }
    }
}