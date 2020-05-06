package com.example.bindingmvvmpractice



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MoodListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<MoodListAdapter.MoodViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var Moods = emptyList<Mood>() // Cached copy of Moods

    inner class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moodItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return MoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val current = Moods[position]
        holder.moodItemView.text = current.mood
    }

    internal fun setMoods(Moods: List<Mood>) {
        this.Moods = Moods
        notifyDataSetChanged()
    }

    override fun getItemCount() = Moods.size
}