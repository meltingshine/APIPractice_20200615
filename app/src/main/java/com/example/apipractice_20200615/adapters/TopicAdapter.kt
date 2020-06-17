package com.example.apipractice_20200615.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.apipractice_20200615.R
import com.example.apipractice_20200615.datas.Topic

class TopicAdapter(
    val mContext: Context,
    val resId: Int,
    val list: List<Topic>
) : ArrayAdapter<Topic>(mContext, resId, list) {


    val inf = LayoutInflater.from(mContext)

    //뒤에 ? 찍은건 null일수도 있다는 뜻
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        tempRow?.let {
        }.let {
            tempRow = inf.inflate(R.layout.topic_list_item,null)
        }
        val row = tempRow!!



        return row


    }
    }
