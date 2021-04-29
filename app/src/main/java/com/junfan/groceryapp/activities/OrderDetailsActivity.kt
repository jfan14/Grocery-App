package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.ViewPagerAdapterOrderDetails
import com.junfan.groceryapp.fragments.OrderSummaryFragment
import com.junfan.groceryapp.models.Order
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.app_bar.*

class OrderDetailsActivity : AppCompatActivity() {

    //var sessionManager = SessionManager(this)
    lateinit var viewPagerAdapterOrderDetail: ViewPagerAdapterOrderDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        init()
    }

    private fun init() {
        setupToolbar()
        var order = intent.getSerializableExtra("ORDER") as Order
        Log.d("jundebugs", "${order.toString()}")

        viewPagerAdapterOrderDetail = ViewPagerAdapterOrderDetails(supportFragmentManager)
        viewPagerAdapterOrderDetail.addFragment(order)
        view_pager_details.adapter = viewPagerAdapterOrderDetail
        tab_layout_details.setupWithViewPager(view_pager_details)


    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Order Details"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sessionManager = SessionManager(this)
        when(item.itemId){
            R.id.menu_cart -> Toast.makeText(applicationContext, "Cart", Toast.LENGTH_SHORT).show()
            R.id.menu_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> {
                sessionManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            android.R.id.home -> {
                startActivity(Intent(this, OrderHistoryActivity::class.java))
            }
            R.id.menu_account -> {
                startActivity(Intent(this, AccountActivity::class.java))
            }
        }
        return true
    }
}