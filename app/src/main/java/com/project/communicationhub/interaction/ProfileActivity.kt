package com.project.communicationhub.interaction

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.LayoutParams.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.project.communicationhub.MainActivity
import com.project.communicationhub.R
import com.project.communicationhub.auth.LoginActivity
import com.project.communicationhub.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class ProfileActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var profileActivity: ActivityProfileBinding

    //Variable to set date
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()
    private var ref: DocumentReference = database.collection("users").document(auth.uid!!)
    private lateinit var downloadUrl: String
    private var editable: Boolean = false
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileActivity = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileActivity.root)

        setSupportActionBar(profileActivity.toolbarProfile)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
        }

        fetchData()

        profileActivity.edit.setOnClickListener {
            editable = true

            profileActivity.name.isClickable = true
            profileActivity.name.isFocusableInTouchMode = true
            profileActivity.name.isFocusable = true
            profileActivity.name.requestFocus()

            profileActivity.dob.isClickable = true

            profileActivity.statusDetail.isClickable = true
            profileActivity.statusDetail.isFocusableInTouchMode = true
            profileActivity.statusDetail.isFocusable = true
            profileActivity.status.requestFocus()

            val params: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            params.setMargins(75, 60, 75, 0)
            profileActivity.genderDetails.layoutParams = params
            profileActivity.gender.visibility = View.VISIBLE
            profileActivity.cancelBtn.visibility = View.VISIBLE
            profileActivity.submitBtn.visibility = View.VISIBLE
        }

        profileActivity.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        profileActivity.userImage.setOnClickListener {
            if (editable) {
                checkPermissionForImage()
            }
        }

        profileActivity.dob.setOnClickListener {
            if (editable) {
                val calender = Calendar.getInstance()
                val year = calender.get(Calendar.YEAR)
                val month = calender.get(Calendar.MONTH)
                val day = calender.get(Calendar.DAY_OF_MONTH)
                val dialog = DatePickerDialog(
                    this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateSetListener,
                    year,
                    month,
                    day
                )
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }
        }

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = "$day/${month + 1}/$year"
            profileActivity.dob.setText(date)
        }

        profileActivity.cancelBtn.setOnClickListener {
            editable = false

            profileActivity.statusDetail.hint = "Enter your status"

            profileActivity.name.isClickable = false
            profileActivity.name.isFocusableInTouchMode = false
            profileActivity.name.isFocusable = false

            profileActivity.dob.isClickable = false

            profileActivity.statusDetail.isClickable = false
            profileActivity.statusDetail.isFocusableInTouchMode = false
            profileActivity.statusDetail.isFocusable = false

            profileActivity.gender.visibility = View.GONE

            val params: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            params.setMargins(75, 60, 75, 69)
            profileActivity.genderDetails.layoutParams = params
            profileActivity.cancelBtn.visibility = View.GONE
            profileActivity.submitBtn.visibility = View.GONE
        }


        profileActivity.submitBtn.setOnClickListener {
            editable = false

            profileActivity.statusDetail.hint = "Enter your status"

            profileActivity.name.isClickable = false
            profileActivity.name.isFocusableInTouchMode = false
            profileActivity.name.isFocusable = false

            profileActivity.dob.isClickable = false

            profileActivity.statusDetail.isClickable = false
            profileActivity.statusDetail.isFocusableInTouchMode = false
            profileActivity.statusDetail.isFocusable = false

            profileActivity.gender.visibility = View.GONE

            profileActivity.submitBtn.isEnabled = false

            val name = profileActivity.name.text.toString()
            val dob = profileActivity.dob.text.toString()
            val status = profileActivity.status.editText?.text.toString()
            val gender = when {
                profileActivity.male.isChecked -> {
                    "Male"
                }
                profileActivity.female.isChecked -> {
                    "Female"
                }
                else -> {
                    "Other"
                }
            }
            profileActivity.genderDetails.text = "Gender: $gender"
            if (!::downloadUrl.isInitialized) {
                Toast.makeText(this, "Photo cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (name.isEmpty()) {
                Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (dob.isEmpty()) {
                Toast.makeText(this, "DOB cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (status.isEmpty()) {
                Toast.makeText(this, "Status cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (gender.isEmpty()) {
                Toast.makeText(this, "Please select your gender!", Toast.LENGTH_SHORT).show()
            } else {
                val delim = ":"
                val arr = dob.split(delim).toTypedArray()
                val userDob = arr[1]
                userDob.trim()
                database.collection("users").document(auth.uid!!).update(
                    "name",
                    name,
                    "dob",
                    userDob,
                    "status",
                    status,
                    "gender",
                    gender,
                    "imageUrl",
                    downloadUrl,
                    "thumbImage",
                    downloadUrl
                ).addOnSuccessListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    profileActivity.submitBtn.isEnabled = true
                }
                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                params.setMargins(75, 60, 75, 69)
                profileActivity.genderDetails.layoutParams = params
                profileActivity.submitBtn.visibility = View.GONE
            }
        }

    }

    private fun fetchData() {
        ref.get().addOnSuccessListener {
            if (it.exists()) {
                if (auth.uid == it.get("uid")) {
                    profileActivity.name.setText(it.getString("name").toString())
                    profileActivity.dob.setText("DOB: " + it.getString("dob").toString())
                    profileActivity.status.editText?.setText(it.getString("status").toString())
                    profileActivity.statusDetail.hint = "Your status"
                    profileActivity.phoneNumber.text = it.getString("phoneNumber").toString()
                    profileActivity.genderDetails.text =
                        "Gender: " + it.getString("gender").toString()
                    val userImgUrl = it.getString("imageUrl").toString()
                    Picasso.get()
                        .load(userImgUrl)
                        .placeholder(R.drawable.defaultavatar)
                        .error(R.drawable.defaultavatar)
                        .into(profileActivity.userImage)
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong, Please try again!!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        startActivity(
            Intent(this, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }


    private fun checkPermissionForImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionWrite = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(
                    permission,
                    1001
                ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_READ LIKE 1001
                requestPermissions(
                    permissionWrite,
                    1002
                ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_WRITE LIKE 1002
            } else {
                pickImageFromGallery()
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            1000
        ) // GIVE AN INTEGER VALUE FOR IMAGE_PICK_CODE LIKE 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            data?.data?.let {
                profileActivity.userImage.setImageURI(it)
                startUpload(it)
            }
        }
    }

    private fun startUpload(filePath: Uri) {
        profileActivity.submitBtn.isEnabled = false
        progressDialog = createProgressDialog("Uploading Image...", false)
        progressDialog.show()
        val ref = storage.reference.child("uploads/" + auth.uid.toString())
        val uploadTask = ref.putFile(filePath)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUrl = task.result.toString()
                progressDialog.dismiss()
                profileActivity.submitBtn.isEnabled = true
            } else {
                profileActivity.submitBtn.isEnabled = true
                // Handle failures
                Toast.makeText(this, "Something went wrong. Please try again!", Toast.LENGTH_LONG)
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong. Please try again!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun createProgressDialog(message: String, isCancelable: Boolean): ProgressDialog {
        return ProgressDialog(this).apply {
            setCancelable(isCancelable)
            setCanceledOnTouchOutside(false)
            setMessage(message)
        }
    }

}