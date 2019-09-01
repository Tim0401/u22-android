package com.akeno0810.u22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.User
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

abstract class CSActivity : AppCompatActivity() {

    val mainHandler = Handler(Looper.getMainLooper())
    protected val compositeSubscription = CompositeSubscription()

    override fun onDestroy() {
        compositeSubscription.clear()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
