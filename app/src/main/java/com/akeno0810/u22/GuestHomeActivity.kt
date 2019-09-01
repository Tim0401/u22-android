package com.akeno0810.u22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.Questionnaire
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList
import java.util.HashMap
import kotlinx.android.synthetic.main.activity_guest_home.*
import android.os.Looper
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import com.google.gson.Gson
import org.json.JSONException






class GuestHomeActivity : CSActivity() {

    var adapter: AnqAdapter? = null
    val qList = ArrayList<Questionnaire>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_home)

        supportActionBar?.let {
            it.title = "アンケート"
        } ?: IllegalAccessException("Toolbar cannot be null")

        val eventNames = HashMap<String?, String?>()

        compositeSubscription.add(
            ApiClientManager.apiClient.getHouseEvents(AppState.instance.groupId)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    val qLists = ArrayList<Observable<List<Questionnaire>>>()
                    for (event in it) {
                        eventNames[event.id] = event.name
                        qLists.add(
                            ApiClientManager.apiClient.getHouseEventQuestionnaires(
                                AppState.instance.groupId,
                                event.id!!.toInt()
                            )
                        )
                    }
                    Observable.concat(qLists)

                }
                .doOnNext {
                    for (q in it) {
                        q.event_name = eventNames[q.event_id]
                    }
                    qList += it
                }
                .doOnError {
                }
                .doOnCompleted {
                    Log.d("tag", "response=$qList")
                    adapter = AnqAdapter(this, qList.toList())
                    try {
                        mainHandler.post { lv_q.adapter = adapter }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe())

        // 項目をタップしたときの処理
        lv_q.setOnItemClickListener {parent, view, position, id ->

            val intent = Intent(this, StartAnqActivity::class.java)
            intent.putExtra(StartAnqActivity.ANQ_ID, Gson().toJson(qList[position]))
            startActivity(intent)
        }

    }

    override fun onBackPressed() {

    }
}
