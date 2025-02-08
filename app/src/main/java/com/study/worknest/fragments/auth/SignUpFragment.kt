package com.study.worknest.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.study.worknest.API.services.AuthService
import com.study.worknest.R
import com.study.worknest.data.requests.SignUpRequest

class SignUpFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText = view.findViewById<EditText>(R.id.usernameEditText)
        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val phoneNumberEditText = view.findViewById<EditText>(R.id.phoneNumberEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val signUpRequest = SignUpRequest.Builder()
            signUpRequest.username(usernameEditText.text.toString())
            signUpRequest.email(emailEditText.text.toString())
            signUpRequest.phoneNumber(phoneNumberEditText.text.toString())
            signUpRequest.password(passwordEditText.text.toString())
            if (signUpRequest.isFilled()) {
                AuthService.signUp(signUpRequest.build(), requireContext()) { isSuccess ->
                    if (isSuccess) {
                        val logInFragment = LogInFragment()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, logInFragment)
                            .addToBackStack(null)
                            .commit()
                    } else {
                        Toast.makeText(requireContext(), "Sign Up Error", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Fill all labels", Toast.LENGTH_SHORT).show()
            }
        }
    }
}