package com.akeno0810.u22

import android.content.Intent
import android.os.Bundle
import com.akeno0810.u22.api.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_date_anq.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DateAnqActivity : CSActivity() {

    companion object {
        val ANQ_ID_LIST = "DateAnq_anq_id_list"
        val CURRENT = "DateAnq_current"
        val EVENT_ID = "DateAnq_event_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_anq)

        val qStr = intent.getStringExtra(ANQ_ID_LIST)
        // Gsonで String → Object
        val dates = Gson().fromJson<List<Date>>(qStr, object : TypeToken<List<Date>>() {}.type)
        val current = intent.getIntExtra(CURRENT, 0)
        val eventId = intent.getIntExtra(EVENT_ID, 0)

        tv_date_anq_no.text = (current + 1).toString()
        tv_date_anq_date.text = dates[current].candidate_datetime

        tv_date_anq_o.setOnClickListener { _ ->
            compositeSubscription.add(
                ApiClientManager.apiClient.setHouseEventQuestionnaireDateAnswer(
                    AppState.instance.groupId,
                    eventId,
                    dates[current].questionnaire_id!!.toInt(),
                    dates[current].id!!.toInt(),
                    Answer(true)
                )
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        if (dates.size > current + 1) {
                            val intent = Intent(this, DateAnqActivity::class.java)
                            intent.putExtra(DateAnqActivity.ANQ_ID_LIST, Gson().toJson(dates))
                            intent.putExtra(DateAnqActivity.CURRENT, current + 1)
                            intent.putExtra(DateAnqActivity.EVENT_ID, eventId)
                            startActivity(intent)
                        } else {
                            compositeSubscription.add(
                                ApiClientManager.apiClient.getHouseEventQuestionnaire(
                                    AppState.instance.groupId,
                                    eventId,
                                    dates[current].questionnaire_id!!.toInt()
                                )
                                    .subscribeOn(Schedulers.io())
                                    .doOnNext {
                                        if (it.shops != null) {
                                            val intent = Intent(this, ShopAnqActivity::class.java)
                                            intent.putExtra(ShopAnqActivity.ANQ_ID_LIST, Gson().toJson(it.shops))
                                            intent.putExtra(ShopAnqActivity.CURRENT, 0)
                                            intent.putExtra(ShopAnqActivity.EVENT_ID, eventId)
                                            startActivity(intent)

                                        } else {
                                            val intent = Intent(this, EndAnqActivity::class.java)
                                            startActivity(intent)

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
                    .doOnError {
                    }
                    .doOnCompleted {
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe())
        }

        tv_date_anq_x.setOnClickListener { _ ->
            compositeSubscription.add(
                ApiClientManager.apiClient.setHouseEventQuestionnaireDateAnswer(
                    AppState.instance.groupId,
                    eventId,
                    dates[current].questionnaire_id!!.toInt(),
                    dates[current].id!!.toInt(),
                    Answer(false)
                )
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        if (dates.size > current + 1) {
                            val intent = Intent(this, DateAnqActivity::class.java)
                            intent.putExtra(DateAnqActivity.ANQ_ID_LIST, Gson().toJson(dates))
                            intent.putExtra(DateAnqActivity.CURRENT, current + 1)
                            intent.putExtra(DateAnqActivity.EVENT_ID, eventId)
                            startActivity(intent)
                        } else {
                            compositeSubscription.add(
                                ApiClientManager.apiClient.getHouseEventQuestionnaire(
                                    AppState.instance.groupId,
                                    eventId,
                                    dates[current].questionnaire_id!!.toInt()
                                )
                                    .subscribeOn(Schedulers.io())
                                    .doOnNext {
                                        if (it.shops != null) {
                                            val intent = Intent(this, ShopAnqActivity::class.java)
                                            intent.putExtra(ShopAnqActivity.ANQ_ID_LIST, Gson().toJson(it.shops))
                                            intent.putExtra(ShopAnqActivity.CURRENT, 0)
                                            intent.putExtra(ShopAnqActivity.EVENT_ID, eventId)
                                            startActivity(intent)

                                        } else {
                                            val intent = Intent(this, EndAnqActivity::class.java)
                                            startActivity(intent)

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
                    .doOnError {
                    }
                    .doOnCompleted {
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe())
        }
    }
}
