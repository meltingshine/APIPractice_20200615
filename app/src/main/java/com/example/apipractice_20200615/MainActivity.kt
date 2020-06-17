package com.example.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apipractice_20200615.adapters.TopicAdapter
import com.example.apipractice_20200615.datas.Topic
import com.example.apipractice_20200615.datas.User
import com.example.apipractice_20200615.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {
    val topicList = ArrayList<Topic>()
    lateinit var topicAdapter: TopicAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {
    }

    override fun setValues() {

        topicAdapter = TopicAdapter(mContext, R.layout.topic_list_item, topicList)
        topicListView.adapter = topicAdapter

        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val topics = data.getJSONArray("topics")

                for (i in 0..topics.length() - 1) {
                    val topicJson = topics.getJSONObject(i)


                    val topic = Topic.getTopicFromJson(topicJson)
                    topicList.add(topic)
                }

                runOnUiThread {

                    topicAdapter.notifyDataSetChanged()
                }
            }

        })


//
//        ServerUtil.getRequestUserInfo(mContext, object : ServerUtil.JsonResponseHandler {
//            override fun onResponse(json: JSONObject) {
//
//                val data = json.getJSONObject("data")
//                val user = data.getJSONObject("user")
////               val loginUser = User.getUserFromJson(user)
//
//                runOnUiThread {
//                }
//            }
//        })
    }

}