package com.lospollos.phonebook.presenters

import android.os.Bundle
import com.lospollos.phonebook.data.ContactProvider
import com.lospollos.phonebook.models.ContactModel
import com.lospollos.phonebook.view.viewInterfaces.ContactsListView
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ContactsListPresenter : MvpPresenter<ContactsListView>() {

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main.immediate)

    fun getContactsList() {
        var contacts: ArrayList<ContactModel>? = null
        scope.launch {
            contacts = withContext(Dispatchers.IO) {
                ContactProvider().getContacts()
            }
        }.invokeOnCompletion {
            if(it == null) {
                viewState.showContacts(contacts)
            }
        }

    }

    fun onContactClick(contact: ContactModel) {
        val args = Bundle()
        args.putString("name", contact.name)
        args.putString("number", contact.number)
        //viewState.showInfo(args)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}