package com.lospollos.phonebook.presenters

import android.os.Bundle
import com.lospollos.phonebook.data.ContactProvider
import com.lospollos.phonebook.models.ContactModel
import com.lospollos.phonebook.view.viewInterfaces.ContactsListView
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.viewstate.strategy.alias.Skip

@InjectViewState
class ContactsListPresenter() : MvpPresenter<ContactsListView>() {

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main.immediate)

    private val contactProvider = ContactProvider()

    fun getContactsList() {
        var contacts: ArrayList<ContactModel>? = null
        scope.launch {
            contacts = withContext(Dispatchers.IO) {
                contactProvider.getContacts()
            }
        }.invokeOnCompletion {
            if(it == null) {
                viewState.showContacts(contacts)
            }
        }
    }

    @Skip
    fun onContactClick(contact: ContactModel) {
        val args = Bundle()
        args.putString("id", contact.id)
        args.putString("name", contact.name)
        args.putString("number", contact.number)
        args.putString("avatar", contact.colorAvatar)
        viewState.showInfo(args)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    @Skip
    fun onPause(contacts: List<ContactModel>) {
        contactProvider.saveContactsInfo(contacts)
    }

}