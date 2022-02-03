package com.lospollos.phonebook.view.activity

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lospollos.phonebook.R
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lospollos.phonebook.data.ContactProvider

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                ContactProvider().getContacts()
            } else {
                Toast.makeText(
                    this,
                    "Требуется установить разрешения", Toast.LENGTH_LONG
                ).show()
            }
        } //TODO: MVP, Update, fragment, permission to write

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val hasReadContactPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true
            ContactProvider().getContacts()
        } else {
            requestPermission.launch(Manifest.permission.READ_CONTACTS)
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun openContactInfoFragment() {
        navController.navigate(R.id.action_contactsListFragment_to_contactInfoFragment2)
    }

    fun closeContactInfoFragment() {
        navController.popBackStack()
    }

    companion object {
        private var READ_CONTACTS_GRANTED = false
    }

}