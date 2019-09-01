package com.akeno0810.u22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.Event
import com.akeno0810.u22.api.GroupName
import kotlinx.android.synthetic.main.activity_host_home.*
import org.json.JSONException
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class HostHomeActivity : CSActivity() {

    var events: List<Event>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_home)

        supportActionBar?.let {
            it.title = "イベント一覧"
        } ?: IllegalAccessException("Toolbar cannot be null")

        compositeSubscription.add(
            ApiClientManager.apiClient.getHouseEvents(AppState.instance.groupId)
                .subscribeOn(Schedulers.io())
                .doOnNext {

                    events = it.toList()
                    val eventNames = ArrayList<String>()
                    if(events != null){
                        for (q in events!!) {
                            eventNames.add(q.name!!)
                        }
                    }
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventNames.toList())
                    try {
                        mainHandler.post { lv_event.adapter = adapter }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                .doOnError {
                }
                .doOnCompleted {


                }
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe())

        // 項目をタップしたときの処理
        lv_event.setOnItemClickListener {parent, view, position, id ->

            val intent = Intent(this, AnqListActivity::class.java)
            intent.putExtra(AnqListActivity.EVENT_ID, events!![position].id!!.toInt())
            startActivity(intent)

        }

    }

    override fun onBackPressed() {

    }
}
