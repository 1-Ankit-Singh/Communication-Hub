package com.project.communicationhub

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.project.communicationhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var mainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)
    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("JIMS Connect")
        dialog.setMessage("Do you want to close this application?")
        dialog.setCancelable(false)
        dialog.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            finish()
        }
        dialog.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        dialog.create().show()
    }

}