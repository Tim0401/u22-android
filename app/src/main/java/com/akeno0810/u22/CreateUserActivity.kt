package com.akeno0810.u22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.akeno0810.u22.api.*
import kotlinx.android.synthetic.main.activity_create_user.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class CreateUserActivity : CSActivity() {
    companion object {
        val GROUP_NAME = "CreateUser_group_name"
        val GROUP_ID = "CreateUser_group_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        supportActionBar?.let {
            it.title = "新規ユーザー作成"
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        } ?: IllegalAccessException("Toolbar cannot be null")

        val gid = intent.getIntExtra(GROUP_ID, -1)
        val gname = intent.getStringExtra(GROUP_NAME) ?: ""

        bt_create_user.setOnClickListener { _ ->
            var gender = "man"
            if(rb_female.isEnabled){
                gender = "woman"
            }else if(rb_other.isEnabled){
                gender = "other"
            }
            val user = User(
                et_new_name.text.toString(),
                et_new_email.text.toString(),
                et_new_pass.text.toString(),
                gender,
                et_birthday.text.toString()
            )
            compositeSubscription.add(
                ApiUserClientManager.apiUserClient.createUser(user)
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        Log.d("tag", "response=$it")
                        AppState.instance.username = it.username ?: ""
                        AppState.instance.email = it.email ?: ""
                        AppState.instance.password = et_new_pass.text.toString()
                        AppState.instance.userId = it.id!!.toInt()

                        ApiUserClientManager.apiUserClient.login(LoginUser(AppState.instance.email,AppState.instance.password ))
                    }
                    .flatMap {
                        AppState.instance.token = it.token ?: ""
                        if(gid == -1){
                            ApiClientManager.apiClient.createHouse(GroupName(gname))
                        }else{
                            ApiClientManager.apiClient.joinHouse(gid,AppState.instance.userId,GroupName(gname))
                        }
                    }
                    .flatMap {
                        val id = it?.id?.toIntOrNull() ?: gid
                        val name = it?.name ?: ""
                        AppState.instance.groupId = id
                        ApiClientManager.apiClient.joinHouse(id,AppState.instance.userId,GroupName(name))
                    }
                    .doOnNext {
                        val intent = Intent(this, ModeActivity::class.java)
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
