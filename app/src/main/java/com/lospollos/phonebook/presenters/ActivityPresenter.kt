package com.lospollos.phonebook.presenters

import com.lospollos.phonebook.view.viewInterfaces.ActivityView
import moxy.MvpPresenter
import moxy.viewstate.strategy.alias.Skip

class ActivityPresenter: MvpPresenter<ActivityView>() {

    @Skip
    fun onPermissionDenied() {
        viewState.showMessagePermission()
    }

}