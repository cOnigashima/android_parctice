package com.example.bindingmvvmpractice

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("app:facialImage")
fun facialImage(view : ImageView, moodState: MoodStateViewModel.MoodState) {

    when(moodState) {
        MoodStateViewModel.MoodState.VERY_BAD -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.sentiment_very_dissatisfied))
        }
        MoodStateViewModel.MoodState.BAD -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.sentiment_dissatisfied))
        }
        MoodStateViewModel.MoodState.GOOD -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.sentiment_satisfied))
        }
        else -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.sentiment_very_satisfied))
        }
    }
}
