package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.OrderAdapter
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.models.AddressResponse
import com.junfan.groceryapp.models.Order
import com.junfan.groceryapp.models.OrderResponse
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.app_bar.*

class OrderHistoryActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    lateinit var orderList: ArrayList<Order>
    var orderAdapter = OrderAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        init()
    }

    private fun init() {
        sessionManager = SessionManager(this)
        setupToolbar()
        getOrdersDetails()
        recycler_view_order.adapter = orderAdapter
        recycler_view_order.layoutManager = LinearLayoutManager(this)
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Order History"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.menu_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> {
                sessionManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            android.R.id.home -> {
                startActivity(Intent(this, AccountActivity::class.java))
            }
            R.id.menu_account -> {
                startActivity(Intent(this, AccountActivity::class.java))
            }
        }
        return true
    }

    private fun getOrdersDetails() {
        var userid = sessionManager.getUserId()
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getOrders(userid!!),
            Response.Listener {
                var gson = Gson()
                var orderResponse = gson.fromJson(it, OrderResponse::class.java)
                orderList = orderResponse.data
                orderAdapter.setData(orderList)
                Log.d("AG", "$orderList")
                Toast.makeText(applicationContext, "Got data", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }

}