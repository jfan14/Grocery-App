package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junfan.groceryapp.R
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        init()
    }

    private fun init() {
        sessionManager = SessionManager(this)
        text_view_name_account.text = sessionManager.getName()
        text_view_id_account.text = sessionManager.getUserId()

        button_order_history.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }

        button_manage_address.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }
    }
}