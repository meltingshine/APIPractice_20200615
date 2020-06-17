package com.example.apipractice_20200615

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.apipractice_20200615.adapters.TopicAdapter
import com.example.apipractice_20200615.datas.Topic
import com.example.apipractice_20200615.datas.User
import com.example.apipractice_20200615.utils.ContextUtil
import com.example.apipractice_20200615.utils.ContextUtil.Companion.setUserToken
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
        topicListView.setOnItemClickListener { parent, view, position, id ->
            val clickedTopic = topicList[position]
            val myIntent = Intent(mContext,ViewTopicDetailActivity::class.java)
            myIntent.putExtra("topic_id",clickedTopic.id)
            startActivity(myIntent)

        }

        logoutBtn.setOnClickListener {
//            로그아웃 버튼이 눌리면 정말 로그아웃 할건지

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("LOGOUTLOGOUTLOGOUTOUTOUTOUTOUT")
            alert.setMessage("로그아웃 할거에요?")
            alert.setPositiveButton("확인",DialogInterface.OnClickListener { dialog, which ->
                ContextUtil.setUserToken(mContext,"")
                val myIntent = Intent(mContext,LoginActivity::class.java)
                startActivity(myIntent)
                finish()
            })
            alert.setNegativeButton("취소", null) // 할일이 없으면 null
            alert.show()
//            확인버튼을 누르면 실제 로그아웃 처리 진행
//            저장된 토큰을 다시 빈칸으로 돌려주자

        }
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