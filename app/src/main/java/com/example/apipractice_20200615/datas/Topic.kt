package com.example.apipractice_20200615.datas

import org.json.JSONObject

class Topic {
    var id = 0
    var title = ""
    var imageUrl = ""


    val sideList = ArrayList<TopicSide>()
    val replyList = ArrayList<TopicReply>()

    var mySideId = 0
    //    내가 선택한 진영이 첫번째인지 두번째인지 선택 안했는지 기록하는 변수
    var mySelectedSideIndex = -1

    companion object {
        fun getTopicFromJson(json:JSONObject) : Topic {
            val topic = Topic()

            topic.id = json.getInt("id")
            topic.title = json.getString("title")
            topic.imageUrl = json.getString("img_url")

//            선택 가능 진영 정보 파싱 => JSONArray 파싱부터
            val sides = json.getJSONArray("sides")

            for(i in 0..sides.length()-1){
                val side = sides.getJSONObject(i)
                
//                json을 topicSide로 변환한거임.
//                어떻게해야 주제의 하위 항목이 될까?
//                해당 주제 진영 배열의 재료로 추가
                val topicSide = TopicSide.getTopicSideFromJson(side)
                topic.sideList.add(topicSide)
            }

            topic.mySideId = json.getInt("my_side_id") // 투표값 없으면 서버에서 -1로 내려줌

//            주제의 모든 진영 중에 내 진영 id와 id값이 같은 진영이 있는지? 검사

            for(i in topic.sideList.indices){
                if(topic.sideList[i].id == topic.mySideId)
                    topic.mySelectedSideIndex = i

            }

            val replies = json.getJSONArray("replies")
            for (i in 0..replies.length()-1){
                topic.replyList.add(TopicReply.getTopicReplyFromJson(replies.getJSONObject(i)))
            }

            return topic

        }
    }

}