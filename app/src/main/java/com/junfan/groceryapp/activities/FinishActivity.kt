package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.junfan.groceryapp.R
import com.junfan.groceryapp.session.SessionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.android.synthetic.main.app_bar.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        init()
    }

    private fun init() {
        Picasso
            .get()
            .load("https://lh3.googleusercontent.com/proxy/G6ge0vUffRpsISiBIIU4pDSCW5B6oKVeSmCVi11mvS8nFwf9zx1de1-7q1Dzixv39blIAD_9my4iwbwiC-BZfw_dcUb08Nw")
            .into(success_icon)

        button_back_to_main.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}