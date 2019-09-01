package com.akeno0810.u22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_group.*

class NewGroupActivity : CSActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)

        supportActionBar?.let {
            it.title = "家族を作成"
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        } ?: IllegalAccessException("Toolbar cannot be null")

        bt_create.setOnClickListener { _ ->
            val intent = Intent(this, CreateUserActivity::class.java)
            intent.putExtra(CreateUserActivity.GROUP_NAME, et_gname.text.toString())
            startActivity(intent)
        }
    }
}
