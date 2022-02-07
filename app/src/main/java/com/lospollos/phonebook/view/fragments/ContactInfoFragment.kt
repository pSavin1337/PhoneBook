package com.lospollos.phonebook.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lospollos.phonebook.R
import com.lospollos.phonebook.view.viewInterfaces.ContactsUpdateView
import moxy.MvpFragment

class ContactInfoFragment : MvpFragment(), ContactsUpdateView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_info, container, false)
    }

}