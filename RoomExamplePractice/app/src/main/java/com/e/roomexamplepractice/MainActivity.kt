package com.e.roomexamplepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputEditText = findViewById<EditText>(R.id.input_text)

        val showTextView = findViewById<TextView>(R.id.show_data)

        val saveButton = findViewById<Button>(R.id.save_button)

        saveButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                AppDatabaseSingleton.getDatabase(this@MainActivity).contentDao().create(
                    Content(1, inputEditText.text.toString())
                )
            }
        }

        findViewById<Button>(R.id.show_button).apply {
            setOnClickListener{
                //　クエリと表示処理
                // GlobalScopeはよくない
                GlobalScope.launch(Dispatchers.IO) {
                    var result : Content? = AppDatabaseSingleton.getDatabase(this@MainActivity).contentDao().getOne(1)
                    //mainThreadに戻して下さい
                    GlobalScope.launch(Dispatchers.Main ){
                        showTextView.text = result?.contentText ?: "なにもなし"
                    }
                }
            }
        }
    }
}
