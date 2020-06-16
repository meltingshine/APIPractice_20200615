package com.example.apipractice_20200615.datas

import org.json.JSONObject

class Topic {
    var id = 0
    var title = ""
    var imageUrl = ""

    companion object {
        fun getTopicFromJson(json:JSONObject) : Topic {
            val t = Topic()

            t.id = json.getInt("id")
            t.title = json.getString("title")
            t.imageUrl = json.getString("img_url")


            return t

        }
    }

}