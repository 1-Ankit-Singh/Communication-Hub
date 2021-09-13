package com.project.communicationhub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.communicationhub.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(){

    // Initializing Variables
    private lateinit var signUpOptionsActivity: ActivitySignUpBinding
    private lateinit var phoneNumber: String
    private lateinit var countryCode: String
    private lateinit var alertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpOptionsActivity = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpOptionsActivity.root)


        signUpOptionsActivity.phoneNumberEt.addTextChangedListener {
            signUpOptionsActivity.nextBtn.isEnabled = !(it.isNullOrEmpty() || it.length < 10)
        }

        signUpOptionsActivity.backImageButton1.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signUpOptionsActivity.nextBtn.setOnClickListener {
            checkNumber()
        }

    }

    private fun checkNumber() {
        countryCode = signUpOptionsActivity.ccp.selectedCountryCodeWithPlus
        phoneNumber = countryCode + signUpOptionsActivity.phoneNumberEt.text.toString()

        if (validatePhoneNumber(signUpOptionsActivity.phoneNumberEt.text.toString())) {
            notifyUserBeforeVerify(
                "We will be verifying the phone number:$phoneNumber\n" +
                        "Is this OK, or would you like to edit the number?"
            )
        } else {
            Toast.makeText(this, "Please enter a valid number to continue!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validatePhoneNumber(phone: String): Boolean {
        if (phone.isEmpty()) {
            return false
        }
        return true
    }

    private fun notifyUserBeforeVerify(message: String) {
        alertDialogBuilder = MaterialAlertDialogBuilder(this).apply {
            setMessage(message)
            setPositiveButton("Ok") { _, _ ->
                showLoginActivity()
            }

            setNegativeButton("Edit") { dialog, _ ->
                dialog.dismiss()
            }

            setCancelable(false)
            create()
            show()
        }
    }

    private fun showLoginActivity() {

    }

}