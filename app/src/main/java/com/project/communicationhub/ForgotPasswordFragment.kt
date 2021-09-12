package com.project.communicationhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.communicationhub.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    // Initializing Variables
    lateinit var forgotPasswordFragment: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forgotPasswordFragment = FragmentForgotPasswordBinding.inflate(inflater)

        forgotPasswordFragment.backImageButton2.setOnClickListener {
            (activity as MainActivity).change(LoginFragment())
        }

        return forgotPasswordFragment.root
    }
}