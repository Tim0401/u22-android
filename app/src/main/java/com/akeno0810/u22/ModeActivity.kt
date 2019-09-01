package com.akeno0810.u22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akeno0810.u22.api.ApiClientManager
import com.akeno0810.u22.api.ApiUserClientManager
import com.akeno0810.u22.api.GroupAdmin
import kotlinx.android.synthetic.main.activity_mode.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ModeActivity : CSActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)

        supportActionBar?.let {
            it.title = "モード選択"
        } ?: IllegalAccessException("Toolbar cannot be null")

        bt_choose_admin.setOnClickListener { _ ->
            compositeSubscription.add(
                ApiClientManager.apiClient.updateHouse(AppState.instance.groupId,AppState.instance.userId,GroupAdmin(true))
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        AppState.instance.groupAdmin = true
                        val intent = Intent(this, HostHomeActivity::class.java)
                        startActivity(intent)
                    }
                    .doOnError {
                    }
                    .doOnCompleted {
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe())
        }
        bt_choose_member.setOnClickListener { _ ->
            compositeSubscription.add(
                ApiClientManager.apiClient.updateHouse(
                    AppState.instance.groupId,
                    AppState.instance.userId,
                    GroupAdmin(false)
                )
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        AppState.instance.groupAdmin = false
                        val intent = Intent(this, GuestHomeActivity::class.java)
                        startActivity(intent)
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
