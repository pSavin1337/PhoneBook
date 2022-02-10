package com.lospollos.phonebook.view.viewInterfaces

import android.os.Bundle
import com.lospollos.phonebook.models.ContactModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

@AddToEnd
interface ContactsListView: MvpView {
    fun showContacts(contacts: ArrayList<ContactModel>?)
    @Skip
    fun showInfo(contact: Bundle)
    @Skip
    fun closeInfo()
}