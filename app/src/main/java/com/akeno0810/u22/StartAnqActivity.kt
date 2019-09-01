package com.akeno0810.u22

import android.content.Intent
import android.os.Bundle
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.Questionnaire
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_start_anq.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class StartAnqActivity : CSActivity() {
    companion object {
        val ANQ_ID = "StartAnq_anq_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_anq)

        val qStr = intent.getStringExtra(ANQ_ID)
        // Gsonで String → Object
        val q = Gson().fromJson<Questionnaire>(qStr, Questionnaire::class.java)

        bt_anq_start_next.setOnClickListener { _ ->
            compositeSubscription.add(
                ApiClientManager.apiClient.getHouseEventQuestionnaire(
                    AppState.instance.groupId,
                    q.event_id!!.toInt(),
                    q.id!!.toInt()
                )
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        if (it.dates != null) {
                            val intent = Intent(this, DateAnqActivity::class.java)
                            intent.putExtra(DateAnqActivity.ANQ_ID_LIST, Gson().toJson(it.dates))
                            intent.putExtra(DateAnqActivity.CURRENT, 0)
                            intent.putExtra(DateAnqActivity.EVENT_ID, q.event_id!!.toInt())
                            startActivity(intent)
                        } else if (it.shops != null) {
                            val intent = Intent(this, ShopAnqActivity::class.java)
                            intent.putExtra(ShopAnqActivity.ANQ_ID_LIST, Gson().toJson(it.shops))
                            intent.putExtra(ShopAnqActivity.CURRENT, 0)
                            intent.putExtra(ShopAnqActivity.EVENT_ID, q.event_id!!.toInt())
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
}
