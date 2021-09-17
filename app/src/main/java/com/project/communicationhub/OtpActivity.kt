package com.project.communicationhub

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.project.communicationhub.databinding.ActivityOtpBinding
import java.util.concurrent.TimeUnit

const val PHONE_NUMBER = "phoneNumber"

class OtpActivity : AppCompatActivity(), View.OnClickListener {

    // Initializing Variables
    private lateinit var otpActivity: ActivityOtpBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var phoneNumber: String? = null
    private var mVerificationId: String? = null
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var progressDialog: ProgressDialog
    private var mCounterDown: CountDownTimer? = null
    private var timeLeft: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpActivity = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(otpActivity.root)

        initView()
        startVerify()

    }

    private fun startVerify() {
        startPhoneNumberVerification(phoneNumber!!)
        showTimer(60000)
        progressDialog = createProgressDialog("Sending a verification code", false)
        progressDialog.show()
    }

    private fun initView() {
        phoneNumber = intent.getStringExtra(PHONE_NUMBER)
        otpActivity.verify.text = getString(R.string.verify_number, phoneNumber)
        setSpannableString()

        // init click listener
        otpActivity.verification.setOnClickListener(this)
        otpActivity.resend.setOnClickListener(this)

        // init fire base verify Phone number callback
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                if (::progressDialog.isInitialized) {
                    progressDialog.dismiss()
                }

                val smsMessageSent = credential.smsCode
                if (!smsMessageSent.isNullOrBlank())
                    otpActivity.sentCode.setText(smsMessageSent)

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                if (::progressDialog.isInitialized) {
                    progressDialog.dismiss()
                }

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.e("Exception:", "FirebaseAuthInvalidCredentialsException", e)
                    Log.e("=========:", "FirebaseAuthInvalidCredentialsException " + e.message)


                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.e("Exception:", "FirebaseTooManyRequestsException", e)
                }

                // Show a message and update the UI
                notifyUserAndRetry("Your Phone Number might be wrong or connection error.Retry again!")

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                //for low level version which doesn't do auto verification save the verification code and the token

                progressDialog.dismiss()
                otpActivity.counter.isVisible = false
                // Save verification ID and resending token so we can use them later
                // Log.e("onCodeSent==", "onCodeSent:$verificationId")

                mVerificationId = verificationId
                mResendToken = token

            }
        }
    }

    private fun setSpannableString() {
        val span = SpannableString(getString(R.string.waiting_text, phoneNumber))
        val clickSpan: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor    // you can use custom color
                ds.isUnderlineText = false // this remove the underline
            }

            override fun onClick(textView: View) { // handle click event
                showLoginActivity()
            }
        }

        span.setSpan(clickSpan, span.length - 13, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        otpActivity.waiting.movementMethod = LinkMovementMethod.getInstance()
        otpActivity.waiting.text = span
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    if (::progressDialog.isInitialized) {
                        progressDialog.dismiss()
                    }
                    //First Time Login
                    if (task.result.additionalUserInfo?.isNewUser == true) {
                        showSignUpActivity()
                    } else {
                        showSignUpActivity()
                        //showHomeActivity()
                    }
                } else {

                    if (::progressDialog.isInitialized) {
                        progressDialog.dismiss()
                    }

                    notifyUserAndRetry("Your Phone Number Verification is failed. Retry again!")
                }
            }
    }

    private fun notifyUserAndRetry(message: String) {
        MaterialAlertDialogBuilder(this).apply {
            setMessage(message)
            setPositiveButton("Ok") { _, _ ->
                showLoginActivity()
            }

            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            setCancelable(false)
            create()
            show()
        }
    }

    private fun showLoginActivity() {
        startActivity(
            Intent(this, LoginActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }

    private fun showSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // This method will send a code to a given phone number as an SMS
    private fun startPhoneNumberVerification(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,           // Phone number to verify
            60,             // Timeout duration
            TimeUnit.SECONDS,      // Unit of timeout
            this,           // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallbacks
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        mResendToken: PhoneAuthProvider.ForceResendingToken?
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks, // OnVerificationStateChangedCallbacks
            mResendToken
        ) // ForceResendingToken from callbacks
    }

    private fun showTimer(milliesInFuture: Long) {
        otpActivity.resend.isEnabled = false
        mCounterDown = object : CountDownTimer(milliesInFuture, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                otpActivity.counter.isVisible = true
                otpActivity.counter.text = getString(R.string.seconds_remaining
                    ,  millisUntilFinished / 1000)

                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                otpActivity.resend.isEnabled = true
                otpActivity.counter.isVisible = false
            }
        }.start()
    }

    override fun onClick(v: View) {
        when (v) {
            otpActivity.verification -> {
                // try to enter the code by yourself to handle the case
                // if user enter another sim card used in another phone ...
                val code = otpActivity.sentCode.text.toString()
                if (code.isNotEmpty() && !mVerificationId.isNullOrEmpty()) {

                    progressDialog = createProgressDialog("Please wait...", false)
                    progressDialog.show()
                    val credential =
                        PhoneAuthProvider.getCredential(mVerificationId!!, code.toString())
                    signInWithPhoneAuthCredential(credential)
                }
            }

            otpActivity.resend -> {
                if (mResendToken != null) {
                    resendVerificationCode(phoneNumber.toString(), mResendToken)
                    showTimer(60000)
                    progressDialog = createProgressDialog("Sending a verification code", false)
                    progressDialog.show()
                } else {
                    Toast.makeText(this, "Sorry, You Can't request new code now, Please wait...", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("timeLeft", timeLeft)
        outState.putString(PHONE_NUMBER, phoneNumber)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mCounterDown != null) {
            mCounterDown!!.cancel()
        }
    }

    override fun onBackPressed() {

    }

}

fun Context.createProgressDialog(message: String, isCancelable: Boolean): ProgressDialog {
    return ProgressDialog(this).apply {
        setCancelable(isCancelable)
        setCanceledOnTouchOutside(false)
        setMessage(message)
    }
}