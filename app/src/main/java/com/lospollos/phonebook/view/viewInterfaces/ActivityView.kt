package com.lospollos.phonebook.view.viewInterfaces

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

@AddToEnd
interface ActivityView: MvpView {
    fun showMessagePermission()
}