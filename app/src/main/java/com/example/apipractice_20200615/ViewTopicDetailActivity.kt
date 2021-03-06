package com.example.apipractice_20200615

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.apipractice_20200615.adapters.TopicAdapter
import com.example.apipractice_20200615.adapters.TopicReplyAdapter
import com.example.apipractice_20200615.datas.Topic
import com.example.apipractice_20200615.datas.TopicReply
import com.example.apipractice_20200615.utils.ContextUtil
import com.example.apipractice_20200615.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.activity_view.topicTitleTxt
import kotlinx.android.synthetic.main.topic_list_item.*
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_view.topicImg as topicImg1

class ViewTopicDetailActivity : BaseActivity() {
    lateinit var mTopic: Topic
    var mTopicId = -1


    lateinit var mReplyAdapter: TopicReplyAdapter

//    val topicReplyList = ArrayList<TopicReply>()
//    lateinit var topicReplyAdapter: TopicReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        setValues()
        setupEvents()
    }

    override fun setupEvents() {


        voteToFirstSideBtn.setOnClickListener {
            ServerUtil.postRequestVote(
                mContext,
                mTopic.sideList[0].id,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        getTopicDetailFromServer()
                    }


                })


            voteToSecondSideBtn.setOnClickListener {
                ServerUtil.postRequestVote(
                    mContext,
                    mTopic.sideList[1].id,
                    object : ServerUtil.JsonResponseHandler {
                        override fun onResponse(json: JSONObject) {
                            getTopicDetailFromServer()
                        }


                    })
            }
        }
    }

    override fun setValues() {
        mTopicId = intent.getIntExtra("topic_id", -1)

//        서버에서 받아온 주제 정보를 저장할 멤버변수
//
//        topicReplyAdapter = TopicReplyAdapter(mContext, R.layout.topic_reply_list_item, topicReplyList)
//        replyListView.adapter = topicReplyAdapter

        if (mTopicId == -1) { //이상치 거르기 위해 넣는값
            Toast.makeText(mContext, "잘못된 접근이다", Toast.LENGTH_SHORT).show()
            return
        }
//    제대로 id를 받아온 경우 서버에 해당 토픽 진행상황 조회
        getTopicDetailFromServer()
    }

    //        진행상황을 받아와주는 함수
    fun getTopicDetailFromServer() {

        ServerUtil.getRequestTopicDetail(
            mContext,
            mTopicId,
            object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val data = json.getJSONObject("data")
                    val topic = data.getJSONObject("topic")

                    val topicObj = Topic.getTopicFromJson(topic)
                    mTopic = topicObj

                    runOnUiThread {
                        topicTitleTxt.text = mTopic.title
                        Glide.with(mContext).load(mTopic.imageUrl).into(topicImg)

                        firstSideTitleTxt.text = mTopic.sideList[0].title
                        secondSideTitleTxt.text = mTopic.sideList[1].title


                        firstSideVoteCountTxt.text = "${mTopic.sideList[0].voteCount}표"
                        secondSideVoteCountTxt.text = "${mTopic.sideList[1].voteCount}표"

                        if (mTopic.mySelectedSideIndex == -1) {
                            voteToFirstSideBtn.text = "투표하기"
                            voteToSecondSideBtn.text = "투표하기"
                        } else if (mTopic.mySelectedSideIndex == 0) {

                            voteToFirstSideBtn.text = "취소하기"
                            voteToSecondSideBtn.text = "갈아타기"

                        } else {

                            voteToFirstSideBtn.text = "갈아타기"
                            voteToSecondSideBtn.text = "취소하기"
                        }

                        mReplyAdapter = TopicReplyAdapter(
                            mContext,
                            R.layout.topic_reply_list_item,
                            mTopic.replyList
                        )

                        replyListView.adapter = mReplyAdapter
                    }


                }

            })
    }

}




