package com.example.apipractice_20200615.datas

import org.json.JSONObject

class TopicSide {

    var id = 0
    var topicId = 0
    var title = ""
    var voteCount = 0

    companion object {
        //        JSON덩어리( {} <- 요고) input하면 내용이 모두 적힌 TopicSide 개체가 output됨.
        fun getTopicSideFromJson(json: JSONObject): TopicSide {

            val ts = TopicSide()

            ts.id = json.getInt("id")
            ts.topicId = json.getInt("topic_id")
            ts.title = json.getString("title")
            ts.voteCount = json.getInt("vote_count")



            return ts


        }
    }
}