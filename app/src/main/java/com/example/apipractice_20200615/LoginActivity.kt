package com.example.apipractice_20200615

import android.os.Bundle
import android.widget.Toast
import com.example.apipractice_20200615.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun setupEvents() {
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

                        } else {
//                    로그인실패

                            val message = json.getString("message")

                            runOnUiThread {
                                Toast.makeText(mContext,message, Toast.LENGTH_SHORT).show()

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
