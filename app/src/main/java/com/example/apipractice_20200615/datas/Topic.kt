package com.example.apipractice_20200615.datas

import org.json.JSONObject

class Topic {
    var id = 0
    var title = ""
    var imageUrl = ""
    
    val sideList = ArrayList<TopicSide>()
    

    companion object {
        fun getTopicFromJson(json:JSONObject) : Topic {
            val t = Topic()

            t.id = json.getInt("id")
            t.title = json.getString("title")
            t.imageUrl = json.getString("img_url")

//            선택 가능 진영 정보 파싱 => JSONArray 파싱부터
            val sides = json.getJSONArray("sides")

            for(i in 0..sides.length()-1){
                val side = sides.getJSONObject(i)
                
//                json을 topicSide로 변환한거임.
//                어떻게해야 주제의 하위 항목이 될까?
//                해당 주제 진영 배열의 재료로 추가
                val topicSide = TopicSide.getTopicSideFromJson(side)
                t.sideList.add(topicSide)
            }

            return t

        }
    }

}