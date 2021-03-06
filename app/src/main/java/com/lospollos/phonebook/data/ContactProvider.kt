package com.lospollos.phonebook.data

import android.annotation.SuppressLint
import android.content.*
import android.content.Context.MODE_PRIVATE
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
import kotlin.random.Random


class ContactProvider {

    private val avatarProvider = AvatarProvider()

    @SuppressLint("Range")
    fun getContacts(): ArrayList<ContactModel> {
        val contacts = ArrayList<ContactModel>()
        avatarProvider.getAvatarsMap()
        val contentResolver: ContentResolver = context.contentResolver
        val cursor: Cursor? =
            contentResolver.query(
                Phone.CONTENT_URI,
                arrayOf(Phone.DISPLAY_NAME_PRIMARY, Phone.DATA1, Phone._ID),
                null,
                null,
                null
            )
        if (cursor != null) {
            while (cursor.moveToNext()) {

                val name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME_PRIMARY))
                val number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER))
                val id = cursor.getString(cursor.getColumnIndex(Phone._ID))
                val contact = ContactModel(id, name, number, avatarProvider.getAvatarColor(id)!!)
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

    fun saveContactsInfo(contacts: List<ContactModel>) {
        avatarProvider.saveAvatars(contacts)
    }

}
