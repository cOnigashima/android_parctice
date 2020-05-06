package com.example.bindingmvvmpractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


class MoodStateViewModel : ViewModel() {


    /**
     * シークバーの値を格納する
     * */
    // android:progress="@{viewModel._seekBarValue}"はめちゃくちゃ重いのでやめる
    // progressと初期値を合わせておく事
    private val _seekBarValue = MutableLiveData(0)

    /**
     * シークバーの値を表示用に整形した文字列
     * */
    val seekBarValueString: LiveData<String> = Transformations.map(_seekBarValue) {
        "value: $it"
    }

    /**
     * シークバーの値が変化した時に呼び出される
     * */
    fun onSeekBarValueChanged(value: Int) {
        // setValue はMainThreadのみ
        _seekBarValue.postValue(value)
    }


    /**
     * シークバーの値によって、表情を変化させる
     * */
    val moodState : LiveData<MoodState> = Transformations.map(_seekBarValue){
        when{
            it < 25 -> MoodState.VERY_BAD
            it < 50 -> MoodState.BAD
            it < 75 -> MoodState.GOOD
            else -> MoodState.VERY_GOOD
        }
    }

    enum class MoodState(val moodName :String) {
       VERY_BAD("very_bad"),
        BAD ("bad"),
        GOOD("good") ,
        VERY_GOOD("very_good")
    }
}