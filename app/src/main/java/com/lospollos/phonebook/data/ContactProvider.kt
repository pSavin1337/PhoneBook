package com.lospollos.phonebook.data

import android.annotation.SuppressLint
import android.provider.ContactsContract
import android.provider.ContactsContract.Data
import android.provider.ContactsContract.RawContacts
import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import com.lospollos.phonebook.App
import com.lospollos.phonebook.models.ContactModel
import android.content.ContentUris
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.content.ContentProviderOperation

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
                        ),
                        cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID)
                        )
                    )
                contacts.add(contact)
            }
            cursor.close()
        }
        return contacts
    }

    @SuppressLint("Range", "Recycle")
    fun updateContact(contactForUpdate: ContactModel) {
        val contactValues = ContentValues()

        val contactId = contactForUpdate.id

        contactValues.put(RawContacts.ACCOUNT_NAME, contactForUpdate.name)
        val cursor: Cursor? = App.context.contentResolver.query(
            RawContacts.CONTENT_URI,
            arrayOf(RawContacts._ID),
            RawContacts.CONTACT_ID + "=?",
            arrayOf(contactId.toString()),
            null
        )
        var rawContactsId: Int? = null
        if (cursor != null) {
            while (cursor.moveToNext()) {
                rawContactsId = cursor.getInt(cursor.getColumnIndex(RawContacts._ID))
            }
        }
        contactValues.clear()
        contactValues.put(
            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME_PRIMARY,
            contactForUpdate.name
        )
        val contentResolver: ContentResolver = App.context.contentResolver
        contentResolver.update(
            Data.CONTENT_URI,
            contactValues,
            "${Data.RAW_CONTACT_ID}=${rawContactsId}",
            null
        )
    }

}

/*
val ops = ArrayList<ContentProviderOperation>()

val rawContactUpdateIndex = ops.size
ops.add(ContentProviderOperation.newUpdate(RawContacts.CONTENT_URI)
.withValue(RawContacts.ACCOUNT_NAME, contactForUpdate.name)
.build());

ops.add(
ContentProviderOperation.newUpdate(Data.CONTENT_URI)
.withValue(Data.RAW_CONTACT_ID, rawContactUpdateIndex)
.withValue(Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//.withValue(Phone.NUMBER, "1-800-GOOG-411")
//.withValue(StructuredName.DISPLAY_NAME, "Mike Sullivan")
.build()
)
App.context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)*/
