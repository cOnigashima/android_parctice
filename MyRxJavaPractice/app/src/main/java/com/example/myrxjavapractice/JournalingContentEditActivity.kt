package com.example.myrxjavapractice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myrxjavapractice.JournalingContentsDisplayActivity.Companion.KEY_CONTENT_ID
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_journaling_content_edit.*
import kotlinx.android.synthetic.main.journaling_list_contents_item.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class JournalingContentEditActivity : AppCompatActivity() {

    companion object {
        const val TEN_MINUTE = 600
    }

    var mContentId :Long = 0
    var mContentIdForEdit :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journaling_content_edit)

        mContentIdForEdit = intent.getIntExtra(KEY_CONTENT_ID,0)

        findViewById<Button>(R.id.time_count_button).
            setOnClickListener {
                startCountDown()
        }

        findViewById<Button>(R.id.back_from_content_detail_button).setOnClickListener {
            if (mContentIdForEdit == 0) {
                startActivity(Intent(this, JournalingContentLIstActivity::class.java))
            } else {
                setResult(Activity.RESULT_OK,Intent(this, JournalingContentsDisplayActivity::class.java))
                finish()
            }
        }

        findViewById<Button>(R.id.content_save_button).setOnClickListener {
                saveContent()
        }


        content_date.text = creationDate()


        fillInTextView(mContentIdForEdit)

    }


    fun startCountDown(){

        val timerDisplay = findViewById<TextView>(R.id.timer_display)
        var d : Disposable
        // TODO 一回ボタンを押して開始したら押せないようにするか、停止する。

        val observable : Observable<Long>  =  Observable.interval(1, TimeUnit.SECONDS)
        // d は必要　"The result of subscribe is not used"
         d = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(Function<Long, Long> {
                return@Function TEN_MINUTE - 1 - it //itはどんどんカウントを増していく
            })

            .subscribe{
                timerDisplay.text = String.format(" %02d : %02d ", (it / 60), (it % 60))
            }

    }

    fun saveContent(){
        if(mContentIdForEdit != 0) mContentId = mContentIdForEdit.toLong()

        val inputTitleText = findViewById<TextView>(R.id.journaling_content_title).text.toString()
        val inputEditText = findViewById<EditText>(R.id.journaling_content_detail_text).text.toString()

        val single: Single<Long> =
            AppDatabaseSingleton.getDatabase(this@JournalingContentEditActivity).contentDao().save(
                JournalingContent(mContentId.toInt(), inputTitleText, inputEditText, creationDate())
            )

        val d: Disposable = single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("subscribe", "success")
                    Toast.makeText(this@JournalingContentEditActivity, "保存しました", Toast.LENGTH_LONG).show()
                    mContentId = it
                },
                {
                    Toast.makeText(
                        this@JournalingContentEditActivity,
                        "何かしらエラーが起きました" + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
    }

    fun fillInTextView(id:Int){
        if(id == 0) return

        var d: Disposable = AppDatabaseSingleton.getDatabase(this).contentDao()
            .findById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                journaling_content_detail_text.setText(it.journalingContentText)
            }, {
                // do nothing
            })
    }

    fun creationDate() = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPANESE).format(Date())

}
