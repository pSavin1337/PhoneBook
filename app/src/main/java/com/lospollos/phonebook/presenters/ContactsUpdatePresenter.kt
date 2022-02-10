package com.lospollos.phonebook.presenters

import com.lospollos.phonebook.data.ContactProvider
import com.lospollos.phonebook.models.ContactModel
import com.lospollos.phonebook.view.viewInterfaces.ContactsUpdateView
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.viewstate.strategy.alias.Skip

@InjectViewState
class ContactsUpdatePresenter : MvpPresenter<ContactsUpdateView>() {

    @Skip
    fun updateContacts(contact: ContactModel, newContact: ContactModel) =
        ContactProvider().updateContact(contact, newContact)

}