package com.example.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {
    var mTopicId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        setValues()
        setupEvents()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mTopicId = intent.getIntExtra("topic_id", -1)
        if (mTopicId == -1) { //이상치 거르기 위해 넣는값
            Toast.makeText(mContext, "잘못된 접근이다", Toast.LENGTH_SHORT).show()
            return
        }
//    제대로 id를 받아온 경우 서버에 해당 토픽 진행상황 조회
        getTopicDetailFromServer()
    }

    //        진행상황을 받아와주는 함수
    fun getTopicDetailFromServer() {

        ServerUtil.getRequestTopicDetail(mContext,mTopicId, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

            }

        })
    }

}


