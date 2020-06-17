package com.example.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.apipractice_20200615.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    var isEmailOk = false
    var isNickOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//이메일 입력값이 변경되면 무조건 다시 검사 받으라고 문구 / Boolean값 리셋
        emailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            //문구가 바겼을떄
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailCheckResultTxt.text = "이메일 중복검사 해야됨"
                isEmailOk = false

            }


        })

        signUpBtn.setOnClickListener {

//            회원가입 API 호출하기 전에 자체 검사
//            1) 이메일 중복 검사 통과해야함
            if (!isEmailOk) {
                Toast.makeText(mContext, "이메일 중복검사를 통과해야 합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            2) 닉네임 중복검사 통과해야함
            if (!isNickOk) {
                Toast.makeText(mContext, "닉네임 중복검사를 통과해야 합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val inputPassword = passwordEdt.text.toString()
//            3) 비번은 8글자 이상 이어야 함
            if (inputPassword.length < 8) {
                Toast.makeText(mContext, "비번은 8글자 이상이야", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            각 순서대로 검사해서 => 어디로 틀렸는지 토스트로 띄우고 클릭이벤트 종료
            val inputEmail = emailEdt.text.toString()
            val inputNickname = nickNameEdt.text.toString()

            ServerUtil.putRequestSignUp(
                mContext,
                inputEmail,
                inputPassword,
                inputNickname,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

                        val code = json.getInt("code")

                        if (code == 200) {
                            runOnUiThread {
                                Toast.makeText(mContext, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }

                    }

                })


        }



        emailCheckBtn.setOnClickListener {

//            입력한 이메일 받아오기
            val email = emailEdt.text.toString()

//            서버에 중복확인 요청
            ServerUtil.getRequestDuplicatedCheck(
                mContext,
                "EMAIL",
                email,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

                        val code = json.getInt("code")

                        runOnUiThread {
//                        UI처리 쓰레드에서 결과 확인 / 화면 반영
                            if (code == 200) {
                                emailCheckResultTxt.text = "사용해도 좋습니다."
                                isEmailOk = true
                            } else {
                                emailCheckResultTxt.text = "중복된 이메일이라 사용 불가합니다."
                            }
                        }


                    }

                })
        }

        nickNameCheckBtn.setOnClickListener {

//            입력한 닉네임 받아오기
            val nick = nickNameEdt.text.toString()

//            서버에 중복확인 요청
            ServerUtil.getRequestDuplicatedCheck(
                mContext,
                "NICK_NAME",
                nick,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

                        val code = json.getInt("code")

                        runOnUiThread {
//                        UI처리 쓰레드에서 결과 확인 / 화면 반영
                            if (code == 200) {
                                nickNameCheckResultTxt.text = "사용해도 좋습니다."
                                isNickOk = true
                            } else {
                                nickNameCheckResultTxt.text = "중복된 닉네임이라 사용 불가합니다."
                            }
                        }


                    }

                })

        }

    }

    override fun setValues() {

    }


}

