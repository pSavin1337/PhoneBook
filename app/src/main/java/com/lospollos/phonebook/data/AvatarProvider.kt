package com.lospollos.phonebook.data

import android.content.Context
import com.lospollos.phonebook.App
import com.lospollos.phonebook.models.ContactModel
import kotlin.random.Random

/**
* format: id1:col1;id2:col2
**/

class AvatarProvider {

    private val name = "Avatars"
    private val spKey = "avatars"

    private lateinit var colorMap: Map<String, String>

    fun getAvatarsMap() {
        val sharedPreferences = App.context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val spAvatars = sharedPreferences.getString(spKey, "")
        colorMap = if (spAvatars == "") HashMap() else toHashMap(spAvatars!!)
    }

    fun saveAvatars(contacts: List<ContactModel>) {
        val sharedPreferences = App.context.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
        val avatars = HashMap<String, String>()
        contacts.forEach { contact ->
            avatars[contact.id] = contact.colorAvatar
        }
        sharedPreferences.putString(spKey, toString(avatars))
        sharedPreferences.apply()
    }

    fun getAvatarColor(id: String): String? {
        var avatarColor: String? = null
        colorMap.forEach { avatar ->
            if (avatar.key == id) {
                avatarColor = avatar.value
            }
        }
        if (avatarColor == null) {
            avatarColor = getNewAvatar()
        }
        return avatarColor
    }

    private fun getNewAvatar() : String {
        val hexColorMax = 999999
        var randomColor = Random.nextInt(hexColorMax).toString()
        val hexColorLength = 6
        while(randomColor.length < hexColorLength) {
            randomColor = "0$randomColor"
        }
        return "#$randomColor"
    }

    private fun toHashMap(colorsString: String) : Map<String, String> {
        val res = HashMap<String, String>()
        colorsString.split(';').forEach { pair ->
            val pairArr = pair.split(':')
            res[pairArr[0]] = pairArr[1]
        }
        return res
    }

    private fun toString(map: Map<String, String>) : String {
        var res = ""
        map.forEach {
            res += "${it.key}:${it.value};"
        }
        return res.dropLast(n = 1)
    }

}