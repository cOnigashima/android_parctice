package com.example.myrxjavapractice

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class JournalingContentLIstActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journaling_contents_list_recycler)

        //テストデータの生成
        val date = SimpleDateFormat("yyyy/MM/dd").format(Date())

//        val memoList = mutableListOf<JournalingContent>()
//        repeat((0..100).count()) { memoList
//            .add(JournalingContent(2,"僕の名前は麻婆", "うんちうんち",date)) }

//        var journalingList :List<JournalingContent>
        var d : Disposable = AppDatabaseSingleton.getDatabase(this).contentDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(this, "拾ってきたよ", Toast.LENGTH_LONG).show()

                    //RecyclerViewにAdapterとLayoutManagerを設定
                    findViewById<RecyclerView>(R.id.journaling_list_container_recycler_view)
                        .also { recyclerView: RecyclerView ->
                            recyclerView.adapter = JournalingContentsListAdapter(this, it)
                            recyclerView.layoutManager = LinearLayoutManager(this)
                        }                }
                ,{
                Toast.makeText(this, "エラーだよ", Toast.LENGTH_LONG).show()
                })
    }
}