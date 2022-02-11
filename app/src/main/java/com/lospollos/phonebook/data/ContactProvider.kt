package com.lospollos.phonebook.data

import android.annotation.SuppressLint
import android.content.*
import android.provider.ContactsContract
import android.provider.ContactsContract.Data
import android.provider.ContactsContract.RawContacts
import android.database.Cursor
import com.lospollos.phonebook.App
import com.lospollos.phonebook.models.ContactModel
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Phone
import com.lospollos.phonebook.App.Companion.context
import java.lang.Exception


class ContactProvider {

    @SuppressLint("Range")
    fun getContacts(): ArrayList<ContactModel> {
        val contacts = ArrayList<ContactModel>()
        val contentResolver: ContentResolver = context.contentResolver
        val cursor: Cursor? =
            contentResolver.query(
                Phone.CONTENT_URI,
                arrayOf(Phone.DISPLAY_NAME_PRIMARY, Phone.DATA1),
                null,
                null,
                null
            )
        if (cursor != null) {
            while (cursor.moveToNext()) {

                val contact =
                    ContactModel(
                        cursor.getString(
                            cursor.getColumnIndex(Phone.DISPLAY_NAME_PRIMARY)
                        ),
                        cursor.getString(
                            cursor.getColumnIndex(Phone.NUMBER)
                        )
                    )
                contacts.add(contact)
            }
            cursor.close()
        }
        return contacts
    }

    fun updateContact(contact: ContactModel, contactForUpdate: ContactModel): Boolean {

        var where =
            "${Data.MIMETYPE} = '${StructuredName.CONTENT_ITEM_TYPE}' AND ${StructuredName.GIVEN_NAME} = ?"
        val args = arrayOf(contact.name)
        val operations: ArrayList<ContentProviderOperation> = ArrayList()
        operations.add(
            ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                .withSelection(where, args)
                .withValue(
                    StructuredName.DISPLAY_NAME_PRIMARY,
                    contactForUpdate.name
                )
                .build()
        )

        where = "${Data.MIMETYPE} = '${Phone.CONTENT_ITEM_TYPE}' AND ${Data.DATA1} = ?"

        args[0] = contact.number
        operations.add(
            ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                .withSelection(where, args)
                .withValue(Data.DATA1, contactForUpdate.number)
                .build()
        )

        where =
            "${RawContacts.DISPLAY_NAME_PRIMARY} = ?"
        args[0] = contact.name
        operations.add(
            ContentProviderOperation.newUpdate(RawContacts.CONTENT_URI)
                .withSelection(where, args)
                .withValue(
                    RawContacts.DISPLAY_NAME_PRIMARY,
                    contactForUpdate.name
                )
                .build()
        )

        try {
            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

}
