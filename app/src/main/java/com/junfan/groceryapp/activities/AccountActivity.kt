package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.junfan.groceryapp.R
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import java.text.ParseException
import java.text.SimpleDateFormat

class AccountActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    lateinit var dbHelper:DBHelper
    private var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        init()
    }

    private fun init() {
        setupToolbar()
        dbHelper = DBHelper(this)
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

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Account"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        var item = menu?.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.menu_cart_layout)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.badge_circle
        updateCartCount()
        view.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        return true;
    }

    fun updateCartCount() {
        var count = dbHelper.getQuantity()
        //Log.d("acd2", "$count")
        if(count == 0) {
            Log.d("acd2", "$count")
            textViewCartCount?.visibility = View.GONE
        }else{
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sessionManager = SessionManager(this)
        when(item.itemId){
            R.id.menu_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.menu_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> {
                sessionManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.menu_account -> {
                startActivity(Intent(this, AccountActivity::class.java))
            }
        }
        return true
    }
}