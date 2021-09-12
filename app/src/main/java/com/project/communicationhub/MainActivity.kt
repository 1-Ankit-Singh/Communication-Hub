package com.project.communicationhub

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.project.communicationhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var mainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host,LoginFragment())
            .addToBackStack(null)
            .commit()

    }

    fun change(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.host,fragment).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("JIMS Connect")
        dialog.setMessage("Do you want to close this application?")
        dialog.setCancelable(false)
        dialog.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            finish()
        }
        dialog.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        dialog.create().show()
    }

}