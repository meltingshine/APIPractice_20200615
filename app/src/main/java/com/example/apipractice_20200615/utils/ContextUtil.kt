package com.example.apipractice_20200615.utils

import android.content.Context

class ContextUtil {

    companion object{

//        메모장(prefrence) 파일 이름에 대응 되는 개념 : 변수로 저장
        val prefName = "APIPracticePref"

//        저장할 항목들의 이름을 변수로 생성
        val USER_TOKEN = "USER_TOKEN"

//        항목에 데이터 저장(setter) or 불러오기(getter)

        fun setUserToken (context:Context, token : String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(USER_TOKEN,token).apply()
        }

        fun getUserToken (context: Context) : String{
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(USER_TOKEN,"")!!
            // defValue 저장된거 없을때 돌려줄 값

        }

    }



}