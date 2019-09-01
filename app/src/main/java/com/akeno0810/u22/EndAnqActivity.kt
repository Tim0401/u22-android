package com.akeno0810.u22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akeno0810.u22.R
import kotlinx.android.synthetic.main.activity_end_anq.*

class EndAnqActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_anq)

        bt_end_anq_top.setOnClickListener { _ ->
            val intent = Intent(this, GuestHomeActivity::class.java)
            startActivity(intent)
        }
    }
}
