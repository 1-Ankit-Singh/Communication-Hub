package com.project.communicationhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.communicationhub.databinding.FragmentSignUpOptionsBinding

class SignUpOptionsFragment : Fragment() {

    // Initializing Variables
    private lateinit var signUpOptionsFragment: FragmentSignUpOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpOptionsFragment = FragmentSignUpOptionsBinding.inflate(inflater)

        signUpOptionsFragment.backImageButton1.setOnClickListener {
            (activity as MainActivity).change(LoginFragment())
        }

        return signUpOptionsFragment.root
    }
}