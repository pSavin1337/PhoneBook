package com.lospollos.phonebook.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.lospollos.phonebook.R
import com.lospollos.phonebook.models.ContactModel
import com.lospollos.phonebook.presenters.ContactsListPresenter
import com.lospollos.phonebook.presenters.ContactsUpdatePresenter
import com.lospollos.phonebook.view.activity.MainActivity
import com.lospollos.phonebook.view.viewInterfaces.ContactsUpdateView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ContactInfoFragment : MvpAppCompatFragment(), ContactsUpdateView {

    @InjectPresenter
    lateinit var contactsUpdatePresenter: ContactsUpdatePresenter

    @ProvidePresenter
    fun providePresenter() = ContactsUpdatePresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentContact = ContactModel(
            arguments?.getString("name")!!,
            arguments?.getString("number")!!
        )
        val nameForm = view.findViewById<EditText>(R.id.nameEditText)
        val numberForm = view.findViewById<EditText>(R.id.numberEditText)
        val submitButton = view.findViewById<Button>(R.id.button)
        nameForm.setText(arguments?.getString("name"))
        numberForm.setText(arguments?.getString("number"))
        submitButton.setOnClickListener {
            val newContact = ContactModel(
                nameForm.text.toString(),
                numberForm.text.toString()
            )
            contactsUpdatePresenter.updateContacts(currentContact, newContact)
            (activity as MainActivity).navController.popBackStack()
        }
    }

}