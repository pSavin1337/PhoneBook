package com.lospollos.phonebook.view.viewInterfaces

import android.os.Bundle
import com.lospollos.phonebook.models.ContactModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

@AddToEnd
interface ContactsListView: MvpView {
    fun showContacts(contacts: ArrayList<ContactModel>?)
    fun showInfo(contact: Bundle)
    fun closeInfo()
}