package com.study.worknest.fragments.auth

import android.content.Intent
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
import com.study.worknest.activities.MainActivity
import com.study.worknest.data.auth.OTPData
import com.study.worknest.utils.TokenManager

class VerifyOTPFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verify_otp, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val otpEditText = view.findViewById<EditText>(R.id.otpEditText)
        val sendButton = view.findViewById<Button>(R.id.sendButton)
        val username = arguments?.getString("USERNAME")
        sendButton.setOnClickListener {
            val otp = otpEditText.text.toString()

            if (username != null && otp.isNotEmpty()) {
                AuthService.verifyOTP(OTPData(username, otp), requireContext()) { tokenResponse ->
                    if (tokenResponse != null) {
                        Toast.makeText(requireContext(), "Вход выполнен успешно!", Toast.LENGTH_SHORT).show()
                        TokenManager.getInstance(requireContext()).saveTokens(tokenResponse)
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        requireActivity().finish()
                    } else {
                        Toast.makeText(requireContext(), "Ошибка входа. Проверьте данные.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}