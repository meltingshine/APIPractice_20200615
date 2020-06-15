package com.example.apipractice_20200615.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.internal.http2.Http2Reader
import org.json.JSONObject
import java.io.IOException
import java.util.logging.Handler


class ServerUtil {

    //    JAVA => static에 대응되는 개념
//    어느 객체가 하던 상관 X, 기능만 잘 되면 그만인것들을 모아두는 영역
    companion object {
        val BASE_URL = "http://15.165.177.142" /* 호스트 주소*/

//        중복 체크를 get으로 요청하는 함수
        fun getRequestDuplicatedCheck(mContext: Context, type:String input: String handler: JsonResponseHandler?) {
    val client = OkHttpClient


//    어느 기능 주소로 가는지

//    get방식은 주소의 paratamer를 모두 적어줘야함

//    가공도니 주소를 가지고 NewBuilder로 변환 파라미터 첨부 준비
    val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
}
        uriBuilder.addEncodedQueryParmameter(Type,"tyype")
        uriBuilder.addEncodedQueryParmameter(Value,"tyype")



        //        로그인 기능을 요청하는 포스트
        fun postRequestLogin(
            context: Context,
            email: String,
            pw: String,
            handler: JsonResponseHandler?
        ) {

            //        //클라이언트로 동작해주는 변수
            val client = OkHttpClient()

            //        어느 기능 주소로 가는지 Host와 조합해서 명시
//            val urlString = "${BASE_URL}/user"

            val urlString =  urlBuilder.Build

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()

            //        서버에 전달할 데이터를 담는 과정 (POST- 폼데이터)
            val formData = FormBody.Builder()
                .add("email", email) //email은 위에 postRequestLogin 함수의 멤버변수
                .add("password", pw) //pw가 멤버변수
                .build()


//            서버에 요청할 모든 정보를 담는 request 변수 생성
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
//                .header() //API에서 헤더를 요구하면 여기서 첨부하면 됨.
                .build()

//            ---------------------여기까진 호출할 재료 만드는 과정--------------------


            client.newCall(request).enqueue(object : Callback {
                //                        연결 자체에 실패한 경우
                override fun onFailure(call: Call, e: IOException) {


                }

                //                        서버 연결에 성공한 경우
                override fun onResponse(call: Call, response: Response) {


//                    서버 응답 중 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                        본문 String을 Json으로 변환
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답",json.toString())

//                    JSON 파싱은 => 화면에서 진행하도록 처리.(인터페이스의 역할)
                    handler?.onResponse(json)

                }


            })


        }
    }

    //    서버 통신의 응답 내용을 Activity에 전달해주는 인터페이스
    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }
}