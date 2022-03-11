package com.lospollos.phonebook.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lospollos.phonebook.R
import com.lospollos.phonebook.models.ContactModel
import com.lospollos.phonebook.presenters.ContactsListPresenter
import com.lospollos.phonebook.view.ContactListAdapter
import com.lospollos.phonebook.view.activity.MainActivity
import com.lospollos.phonebook.view.viewInterfaces.ContactsListView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ContactsListFragment : MvpAppCompatFragment(), ContactsListView {

    private var recyclerView: RecyclerView? = null
    private var contacts: List<ContactModel>? = null

    @InjectPresenter
    lateinit var contactsListPresenter: ContactsListPresenter

    @ProvidePresenter
    fun providePresenter() = ContactsListPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contactsListPresenter.getContactsList()
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun onPause() {
        super.onPause()
        if (contacts != null) {
            contactsListPresenter.onPause(contacts!!)
        }
    }

    override fun showContacts(contacts: ArrayList<ContactModel>?) {
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        if (contacts != null) {
            this.contacts = contacts
            val adapter = ContactListAdapter(
                contacts,
                contactsListPresenter::onContactClick
            )
            recyclerView?.adapter = adapter
        }
    }

    override fun showInfo(contact: Bundle) {
        (activity as MainActivity).navController.navigate(
            R.id.action_contactsListFragment_to_contactInfoFragment2,
            contact
        )
    }

    override fun closeInfo() {
        (activity as MainActivity).navController.popBackStack()
    }

}