package com.example.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apipractice_20200615.datas.User
import com.example.apipractice_20200615.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {
    override fun setupEvents() {
    }

    override fun setValues() {

        ServerUtil.getRequestUserInfo(mContext,object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject){

                val data = json.getJSONObject("data")
                val user = data.getJSONObject("user")
               val loginUser = User.getUserFromJson(user)

                runOnUiThread{
                    userNickNameTxt.text =loginUser.nickName
                    userEmailTxt.text = loginUser.email
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()

    }
}