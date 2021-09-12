package com.project.communicationhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.communicationhub.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    // Initializing Variables
    private lateinit var loginFragment: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginFragment = FragmentLoginBinding.inflate(inflater)

        loginFragment.forgotPassword.setOnClickListener {
            (activity as MainActivity).change(ForgotPasswordFragment())
        }

        loginFragment.loginSignup.setOnClickListener {
            (activity as MainActivity).change(SignUpOptionsFragment())
        }

        return loginFragment.root
    }
}