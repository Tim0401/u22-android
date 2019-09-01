package com.akeno0810.u22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akeno0810.u22.R
import com.akeno0810.u22.api.Answer
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.Shop
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_shop_anq.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ShopAnqActivity : CSActivity() {

    companion object {
        val ANQ_ID_LIST = "ShopAnq_anq_id_list"
        val CURRENT = "ShopAnq_current"
        val EVENT_ID = "ShopAnq_event_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_anq)

        val qStr = intent.getStringExtra(ANQ_ID_LIST)
        // Gsonで String → Object
        val shops = Gson().fromJson<List<Shop>>(qStr, object : TypeToken<List<Shop>>() {}.type)
        val current = intent.getIntExtra(CURRENT, 0)
        val eventId = intent.getIntExtra(EVENT_ID, 0)

        tv_shop_anq_no.text = (current + 1).toString()
        tv_shop_anq_shop.text = shops[current].shop_id

        tv_shop_anq_o.setOnClickListener { _ ->
            compositeSubscription.add(
                ApiClientManager.apiClient.setHouseEventQuestionnaireShopAnswer(
                    AppState.instance.groupId,
                    eventId,
                    shops[current].questionnaire_id!!.toInt(),
                    shops[current].id!!.toInt(),
                    Answer(true)
                )
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        if (shops.size > current + 1) {
                            val intent = Intent(this, ShopAnqActivity::class.java)
                            intent.putExtra(ShopAnqActivity.ANQ_ID_LIST, Gson().toJson(shops))
                            intent.putExtra(ShopAnqActivity.CURRENT, current + 1)
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

        tv_shop_anq_x.setOnClickListener { _ ->
            compositeSubscription.add(
                ApiClientManager.apiClient.setHouseEventQuestionnaireShopAnswer(
                    AppState.instance.groupId,
                    eventId,
                    shops[current].questionnaire_id!!.toInt(),
                    shops[current].id!!.toInt(),
                    Answer(false)
                )
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        if (shops.size > current + 1) {
                            val intent = Intent(this, ShopAnqActivity::class.java)
                            intent.putExtra(ShopAnqActivity.ANQ_ID_LIST, Gson().toJson(shops))
                            intent.putExtra(ShopAnqActivity.CURRENT, current + 1)
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
}
