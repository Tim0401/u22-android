package com.akeno0810.u22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.DateAnswer
import com.akeno0810.u22.api.ShopAnswer
import kotlinx.android.synthetic.main.activity_and_detail.*
import org.json.JSONException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.HashMap

class AndDetailActivity : CSActivity() {

    companion object {
        val EVENT_ID = "AndDetail_event_id"
        val ANQ_ID = "AndDetail_anq_id"
    }

    val dayNames = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_and_detail)

        val eventId = intent.getIntExtra(EVENT_ID, 0)
        val anqId = intent.getIntExtra(ANQ_ID, 0)

        val dateNames = HashMap<String?, String?>()
        val sLists = ArrayList<Observable<List<ShopAnswer>>>()

        compositeSubscription.add(
            ApiClientManager.apiClient.getHouseEventQuestionnaire(AppState.instance.groupId, eventId, anqId)
                .subscribeOn(Schedulers.io())
                .flatMap {

                    val qLists = ArrayList<Observable<List<DateAnswer>>>()
                    if(it.dates != null){
                        for (d in it.dates!!) {
                            dateNames[d.id] = d.candidate_datetime
                            qLists.add(
                                ApiClientManager.apiClient.getHouseEventQuestionnaireDateAnswer(
                                    AppState.instance.groupId,
                                    eventId,
                                    anqId,
                                    d.id!!.toInt()
                                )
                            )
                        }
                    }
                    if(it.shops != null){
                        for (d in it.shops!!) {
                            sLists.add(
                                ApiClientManager.apiClient.getHouseEventQuestionnaireShopAnswer(
                                    AppState.instance.groupId,
                                    eventId,
                                    anqId,
                                    d.id!!.toInt()
                                )
                            )
                        }
                    }
                    Observable.concat(qLists)
                }
                .doOnNext {
                    val days = it?.toList()
                    if(days != null){
                        var ok = 0
                        var ng = 0
                        for (q in days!!) {
                            if(q.ok!!){
                                ok++
                            }else{
                                ng++
                            }
                        }
                        if(days.isNotEmpty()){
                            dayNames.add(dateNames[days.first().questionnaire_date_id!!] + "  ◯：" + ok + "  ×：" + ng)
                        }
                        Log.d("tag", "response=$dayNames")
                    }

                }
                .doOnError {
                }
                .doOnCompleted {
                    Log.d("tag", "response=$dayNames")

                    Observable.concat(sLists)
                        .subscribeOn(Schedulers.io())
                        .doOnNext {
                            val days = it?.toList()
                            if(days != null){
                                var ok = 0
                                var ng = 0
                                for (q in days!!) {
                                    if(q.Ok!!){
                                        ok++
                                    }else{
                                        ng++
                                    }
                                }
                                if(days.isNotEmpty()){
                                    dayNames.add("shopID:" + days.first().questionnaire_shop_id + "  ◯：" + ok + "  ×：" + ng)
                                }
                                Log.d("tag", "response=$dayNames")
                            }

                        }
                        .doOnError {
                        }
                        .doOnCompleted {
                            Log.d("tag", "response=$dayNames")
                            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dayNames.toList())
                            try {
                                mainHandler.post { lv_anq_detail.adapter = adapter }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe())
    }
}
