package com.example.apipractice_20200615

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.apipractice_20200615.utils.ContextUtil
import com.example.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setValues()
        setupEvents()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        Handler().postDelayed({

//        자동 로그인 희망 여부
            val isAutoLogin = ContextUtil.isAutoLogin(mContext)
//        저장된 토큰값
            val token = ContextUtil.getUserToken(mContext)

//        자동 로그인 && 토큰도 저장되어 있다면

            if (isAutoLogin && token != "") {
//            서버로 가서 (내폰에) 저장된 토큰으로 내 정보 불러오기

                ServerUtil.getRequestUserInfo(mContext, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

                        val code = json.getInt("code")
                        if (code == 200) {
//                        정상 로그인 된 코드 200
//                        토큰이 유효해서, 내 정보를 잘 받아옴 => 메인화면 이동
                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()

                        } else {
//                        토큰으로 내 정보 조회 실패 => 로그인 화면 이동
                            val myIntent = Intent(mContext, LoginActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }
                    }


                })

            } else {
//            로그인 화면으로 이동 (2초를 넘어야 함..)
                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
                finish()
            }

        }, 2000)

    }
}
