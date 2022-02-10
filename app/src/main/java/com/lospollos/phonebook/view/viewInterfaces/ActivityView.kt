package com.lospollos.phonebook.view.viewInterfaces

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

@AddToEnd
interface ActivityView: MvpView {
    @Skip
    fun showMessagePermission()
}