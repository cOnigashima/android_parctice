package com.example.myrxjavapractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    companion object {
        const val TEN_MINUTE = 600
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.time_count_button).
            setOnClickListener {
                startCountDown()
        }

        findViewById<Button>(R.id.content_save_button).setOnClickListener {
            val inputTitleText =
                findViewById<TextView>(R.id.content_title_journaling).text.toString()
            val inputEditText = findViewById<EditText>(R.id.journaling_content_text).text.toString()
            val df = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE)
            val date = Date()

            val completable: Completable =
                AppDatabaseSingleton.getDatabase(this@MainActivity).contentDao().save(
                    JournalingContent(1, inputTitleText, inputEditText, df.format(date))
                )
            completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {   Log.d("subscribe","success")
                        Toast.makeText(this@MainActivity, "保存しました", Toast.LENGTH_LONG).show()},
                    {
                        Toast.makeText(
                            this@MainActivity,
                            "何かしらエラーが起きました" + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
        }
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
                return@Function TEN_MINUTE - it //itはどんどんカウントを増していく
            })

            .subscribe{
                // もうKotlinがわからない
                // itがいわゆるonNextで返される値。

                // 分数で　format　
                //　これformat使う方がええな。
                //timerDisplay.text = ((it/60).toString() + " : " + (it%60).toString())
                // format("" , intかLongじゃないとダメ。多分Long , )
                // はぁ、楽しかった。。。
                timerDisplay.text = String.format( " %02d : %02d ",(it/60),(it%60))

                // TimeUnit.SECONDS.toMinutes(
                // onCompleteとかはないから？ってとやろう？？
                //TODO 0になったら、トーストを出して、停止する。
                // 分数を選べるようにする。
            }


//                     Javaの場合。これで一生頑張っていた
//                     new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable = d;
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        onTick(aLong);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        onFinish();
//                    }
//                });


    }
}
