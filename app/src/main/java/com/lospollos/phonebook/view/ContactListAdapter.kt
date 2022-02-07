package com.lospollos.phonebook.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lospollos.phonebook.R
import com.lospollos.phonebook.models.ContactModel

class ContactListAdapter(
    var contactsList: ArrayList<ContactModel>,
    var onContactClickCallback: (contact: ContactModel) -> Unit
    ): RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contactTextView: TextView? = null
        init {
            contactTextView = itemView.findViewById(R.id.contactCardTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_recycler_view_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contactName = contactsList[position].name
        holder.contactTextView?.text = contactName
        holder.contactTextView?.setOnClickListener {
            onContactClickCallback(contactsList[position])
        }
    }

    override fun getItemCount(): Int = contactsList.size

}