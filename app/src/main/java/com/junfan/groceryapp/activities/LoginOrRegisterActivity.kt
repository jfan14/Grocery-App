package com.junfan.groceryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junfan.groceryapp.R
import kotlinx.android.synthetic.main.activity_login_or_register.*

class LoginOrRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_register)

        init()
    }

    private fun init() {
        button_register_loginreg.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        button_login_loginreg.setOnClickListener {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        if(getSharedPreferences("my_pref", Context.MODE_PRIVATE).getBoolean("isLoggedIn",false)) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}