package com.akeno0810.u22

import android.annotation.SuppressLint
import android.content.Context

class AppState private constructor(context: Context) {
    val con = context
    var userId = -1
        set(str) {
            field = str
            val data = con.getSharedPreferences("Data", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putInt("userId", field)
            editor.apply()
        }
    var email: String = ""
        set(str) {
            field = str
            val data = con.getSharedPreferences("Data", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putString("email", field)
            editor.apply()
        }
    var password = ""
        set(str) {
            field = str
            val data = con.getSharedPreferences("Data", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putString("password", field)
            editor.apply()
        }
    var username: String = ""
        set(str) {
            field = str
            val data = con.getSharedPreferences("Data", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putString("username", field)
            editor.apply()
        }
    var token = ""
        set(str) {
            field = str
            val data = con.getSharedPreferences("Data", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putString("token", field)
            editor.apply()
        }
    var groupId = -1
        set(str) {
            field = str
            val data = con.getSharedPreferences("Data", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putInt("groupId", field)
            editor.apply()
        }
    var groupAdmin = true
        set(str) {
            field = str
            val data = con.getSharedPreferences("Data", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putBoolean("groupAdmin", field)
            editor.apply()
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var _instance: AppState? = null

        fun onCreateApplication(applicationContext: Context) {
            // Application#onCreateのタイミングでシングルトンが生成される
            _instance = AppState(applicationContext)

            val data = applicationContext.getSharedPreferences("Data", Context.MODE_PRIVATE)
            _instance!!.email = data.getString("email", "") ?: ""
            _instance!!.password = data.getString("password", "") ?: ""
            _instance!!.userId = data.getInt("userId", -1)
            _instance!!.groupId = data.getInt("groupId", -1)
            _instance!!.groupAdmin = data.getBoolean("groupAdmin", true)
            _instance!!.username = data.getString("username", "") ?: ""
            _instance!!.token = data.getString("token", "") ?: ""

            // テスト用

            _instance!!.groupId = 1
            _instance!!.groupAdmin = true
        }

        val instance: AppState
            get() {
                _instance?.let {
                    return it
                } ?: run {
                    // nullにはならないはず
                    throw RuntimeException("AppState should be initialized!")
                }
            }
    }
}