package com.akeno0810.u22

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter
import com.akeno0810.u22.api.Questionnaire
import kotlinx.android.synthetic.main.anq_cell.view.*

class AnqAdapter(internal var context: Context, anqList: List<Questionnaire>) : BaseAdapter() {
    data class ViewHolder(var eventName: TextView, var qName: TextView, var date: TextView)
    var inflater: LayoutInflater = LayoutInflater.from(context)
    internal var AnqList: List<Questionnaire> = anqList.toList()

    fun setAnqList(anqList: List<Questionnaire>) {
        this.AnqList = anqList.toList()
    }

    override fun getCount(): Int {
        return AnqList.size
    }

    override fun getItem(position: Int): Any {
        return AnqList[position]
    }

    override fun getItemId(position: Int): Long {
        return AnqList[position].id!!.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder

        view = inflater.inflate(R.layout.anq_cell, parent, false)
        holder = ViewHolder(
            view?.tv_anq_eventName!!,
            view.tv_anq_name,
            view.tv_anq_datetime
        )
        view.tag = holder

        val q = getItem(position) as Questionnaire
        holder.eventName.text = q.event_name
        holder.qName.text = q.id
        holder.date.text = q.created_at

        return view
    }
}