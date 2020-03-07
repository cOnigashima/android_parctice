package com.example.myrxjavapractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView


class JournalingContentsListAdapter(private val context: Context, private val journalingList: MutableList<JournalingContent>) :
    RecyclerView.Adapter<JournalingContentsListAdapter.JournalingContentViewHolder>() {

    class JournalingContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentTitleTextView: TextView = view.findViewById(R.id.journaling_item_title)
        val dateTextView: TextView = view.findViewById(R.id.journaling_item_create_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalingContentViewHolder =
        JournalingContentViewHolder(LayoutInflater.from(context).inflate(R.layout.journaling_list_contents_item, parent, false))

    override fun getItemCount(): Int = journalingList.size

    override fun onBindViewHolder(holder: JournalingContentViewHolder, position: Int) {
        holder.dateTextView.text = journalingList[position].createDate
        holder.contentTitleTextView.text = journalingList[position].jornalingContentTitle
    }

    fun getContentId(position: Int): Int {
        return journalingList.get(position).journalingContentId
    }

    fun removeItem(position: Int){
        journalingList.remove(journalingList.get(position))
    }


}