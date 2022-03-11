package com.lospollos.phonebook.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lospollos.phonebook.App.Companion.context
import com.lospollos.phonebook.R
import com.lospollos.phonebook.models.ContactModel

class ContactListAdapter(
    private var contactsList: ArrayList<ContactModel>,
    var onContactClickCallback: (contact: ContactModel) -> Unit
    ): RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contactTextView: TextView? = null
        var avatar: ImageView? = null
        init {
            contactTextView = itemView.findViewById(R.id.contactCardTextView)
            avatar = itemView.findViewById(R.id.avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_recycler_view_item, parent, false)
        return ContactViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contactName = contactsList[position].name
        val color = Color.parseColor(contactsList[position].colorAvatar)
        //context.getDrawable(R.drawable.avatar_drawable)?.setTint(color)
        holder.avatar?.setBackgroundColor(color)
        holder.contactTextView?.text = contactName
        holder.contactTextView?.setOnClickListener {
            onContactClickCallback(contactsList[position])
        }
    }

    override fun getItemCount(): Int = contactsList.size

}