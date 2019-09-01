package com.akeno0810.u22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : CSActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        supportActionBar?.let {
            it.title = "家族に参加"
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        } ?: IllegalAccessException("Toolbar cannot be null")

        bt_g_join.setOnClickListener { _ ->
            val intent = Intent(this, CreateUserActivity::class.java)
            intent.putExtra(CreateUserActivity.GROUP_ID, et_gid.text.toString().toIntOrNull() ?: -1)
            intent.putExtra(CreateUserActivity.GROUP_NAME, et_gname2.text.toString())
            startActivity(intent)
        }

    }
}
