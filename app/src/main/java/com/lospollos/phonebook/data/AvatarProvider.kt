package com.lospollos.phonebook.data

import kotlin.random.Random

class AvatarProvider {

    fun getNewAvatar() : String {
        val hexColorMax = 999999
        var randomColor = Random.nextInt(hexColorMax).toString()
        val hexColorLength = 6
        while(randomColor.length < hexColorLength) {
            randomColor = "0$randomColor"
        }
        return "#$randomColor"
    }

    //format: id1:col1;id2:col2
    fun toHashMap(colorsString: String) : Map<String, String> {
        val res = HashMap<String, String>()
        colorsString.split(';').forEach { pair ->
            val pairArr = pair.split(':')
            res[pairArr[0]] = pairArr[1]
        }
        return res
    }

    fun toString(map: Map<String, String>) : String {
        var res = ""
        map.forEach {
            res += "${it.key}:${it.value};"
        }
        return res.dropLast(n = 1)
    }

}