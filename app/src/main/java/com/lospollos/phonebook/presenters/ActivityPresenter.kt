package com.lospollos.phonebook.presenters

import com.lospollos.phonebook.view.viewInterfaces.ActivityView
import moxy.MvpPresenter

class ActivityPresenter: MvpPresenter<ActivityView>() {

    fun onPermissionDenied() {
        viewState.showMessagePermission()
    }

}