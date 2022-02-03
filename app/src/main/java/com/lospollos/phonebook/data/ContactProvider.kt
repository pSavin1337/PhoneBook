package com.lospollos.phonebook.data

import android.annotation.SuppressLint
import android.provider.ContactsContract

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import com.lospollos.phonebook.App
import com.lospollos.phonebook.models.ContactModel


class ContactProvider {

    @SuppressLint("Range")
    fun getContacts(): ArrayList<ContactModel> {
        val contacts = ArrayList<ContactModel>()
        val contentResolver: ContentResolver = App.context.contentResolver
        val cursor: Cursor? =
            contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
            )
        if (cursor != null) {
            while (cursor.moveToNext()) {

                val contact =
                    ContactModel(
                        cursor.getInt(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID)
                        ),
                        cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                        )
                    )
                contacts.add(contact)
            }
            cursor.close()
        }
        return contacts
    }

    @SuppressLint("Range")
    fun updateContact(contactForUpdate: ContactModel) {
        val contentValues = ContentValues()
        contentValues.put(ContactsContract.RawContacts.ACCOUNT_NAME, contactForUpdate.name)
        val contentResolver: ContentResolver = App.context.contentResolver
        contentResolver.update(
            ContactsContract.Contacts.CONTENT_URI,//TODO: maybe not working
            contentValues,
            "_ID=${contactForUpdate.id}",
            null
        )
    }

}