package com.akeno0810.u22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.ApiUserClientManager
import com.akeno0810.u22.api.LoginUser
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : CSActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!AppState.instance.email.isEmpty()) {
            compositeSubscription.add(
                ApiUserClientManager.apiUserClient.login(
                    LoginUser(
                        AppState.instance.email,
                        AppState.instance.password
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        AppState.instance.token = it.token ?: ""
                        if(AppState.instance.groupAdmin){
                            intent = Intent(this, HostHomeActivity::class.java)
                        }else{
                            intent = Intent(this, GuestHomeActivity::class.java)
                        }
                        startActivity(intent)
                    }
                    .doOnError {
                    }
                    .doOnCompleted {
                    }
                    .subscribe())
        }

        bt_main_join.setOnClickListener { _ ->
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        bt_new.setOnClickListener { _ ->
            val intent = Intent(this, NewGroupActivity::class.java)
            startActivity(intent)
        }
    }
}
