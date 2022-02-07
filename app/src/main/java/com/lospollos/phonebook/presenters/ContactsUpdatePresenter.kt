package com.lospollos.phonebook.presenters

import com.lospollos.phonebook.data.ContactProvider
import com.lospollos.phonebook.models.ContactModel
import com.lospollos.phonebook.view.viewInterfaces.ContactsUpdateView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ContactsUpdatePresenter: MvpPresenter<ContactsUpdateView>() {

    fun updateContacts(newContact: ContactModel) = ContactProvider().updateContact(newContact)

}