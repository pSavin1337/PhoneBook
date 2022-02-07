package com.lospollos.phonebook.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lospollos.phonebook.R
import com.lospollos.phonebook.presenters.ActivityPresenter
import com.lospollos.phonebook.view.viewInterfaces.ActivityView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(), ActivityView {

    @InjectPresenter
    lateinit var activityPresenter: ActivityPresenter

    lateinit var navController: NavController

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            var resultPermission = true

            permissions.forEach { actionMap ->
                resultPermission = resultPermission && actionMap.value
            }

            if (!resultPermission) {
                activityPresenter.onPermissionDenied()
                application
            }

        } //TODO: MVP, Update

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.main_container)
    }

    private fun requestPermissions() {
        val hasReadContactPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (hasReadContactPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission.launch(
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS
                )
            )
        }
    }

    override fun showMessagePermission() {
        Toast.makeText(
            this,
            getString(R.string.permissions_denied_text),
            Toast.LENGTH_LONG
        ).show()
    }

}