package com.example.apipractice_20200615

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.apipractice_20200615.utils.ContextUtil
import com.example.apipractice_20200615.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun setupEvents() {
        signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }


        loginBtn.setOnClickListener {
            val email = emailEdt.text.toString()
            val pw = passwordEdt.text.toString()


            //입력한 ID /PW 가 진짜 회원인지 서버에 물어봐야함 (요청 필요)

            ServerUtil.postRequestLogin(
                mContext,
                email,
                pw,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

                        val codeNumber = json.getInt("code")

                        if (codeNumber == 200) {
//                    로그인성공

//                            서버에서 성공시 내려주는 토큰값 추출
                            val data = json.getJSONObject("data")
                            val token = data.getString("token")

//                            폰에 아에 저장해두는게 편리함.
                            ContextUtil.setUserToken(mContext,token)

                        } else {
//                    로그인실패

                            val message = json.getString("message")

                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                            }
                        }
                    }


                })
        }
    }

    override fun setValues() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }
}
