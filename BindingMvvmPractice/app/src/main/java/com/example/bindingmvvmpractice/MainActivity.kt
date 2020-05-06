package com.example.bindingmvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bindingmvvmpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MoodStateViewModel::class.java)
    }

    private lateinit var moodListViewModel: MoodListViewModel

    private lateinit var binding: ActivityMainBinding
    private var progressValue : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.moodStateSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //　TODO observeしていないから、またLiveDataの監視とは違う。通知を飛ばしてくれてる
                // ListenerがあるからそれでLiveDataを弄っているけど、Progressを監視したら、双方向になるかな。どっちのが良い？？
                this@MainActivity.viewModel.onSeekBarValueChanged(progress)
                progressValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MoodListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        moodListViewModel = ViewModelProvider(this).get(MoodListViewModel::class.java)

        moodListViewModel.allMoods.observe(this, Observer { moods ->
            // Update the cached copy of the words in the adapter.
            moods?.let { adapter.setMoods(it) }
        })

        findViewById<Button>(R.id.save_button).setOnClickListener{
            val mood = Mood(0 , viewModel.moodState.value!!.moodName , progressValue)
            moodListViewModel.insert(mood)
        }
    }

}
