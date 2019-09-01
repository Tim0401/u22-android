package com.akeno0810.u22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.Questionnaire
import kotlinx.android.synthetic.main.activity_anq_list.*
import org.json.JSONException
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class AnqListActivity : CSActivity() {

    companion object {
        val EVENT_ID = "AnqList_event_id"
    }

    var qs: List<Questionnaire>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anq_list)

        val eventId = intent.getIntExtra(EVENT_ID, 0)

        supportActionBar?.let {
            it.title = "アンケート一覧"
        } ?: IllegalAccessException("Toolbar cannot be null")

        compositeSubscription.add(
            ApiClientManager.apiClient.getHouseEventQuestionnaires(AppState.instance.groupId, eventId)
                .subscribeOn(Schedulers.io())
                .doOnNext {

                    qs = it.toList()
                    val qNames = ArrayList<String>()
                    if(qs != null){
                        for (q in qs!!) {
                            qNames.add(q.id!!)
                        }
                    }
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, qNames.toList())
                    try {
                        mainHandler.post { lv_anq_list_qs.adapter = adapter }
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
    }
}
