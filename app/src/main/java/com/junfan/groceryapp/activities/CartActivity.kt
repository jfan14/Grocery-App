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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.CartAdapter
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.models.Product
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.button_place_order_cart
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.empty_cart_layout.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import org.json.JSONObject
import kotlin.properties.Delegates

class CartActivity : AppCompatActivity(), CartAdapter.OnAdapterListener {

    lateinit var dbHelper: DBHelper
    lateinit var mList: ArrayList<Product>
    lateinit var cartAdapter: CartAdapter
    private var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


        empty_layout.visibility = View.GONE
        dbHelper = DBHelper(this)
        updateCartCount()
        mList = dbHelper.getAllProduct()
        init()
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

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="My Cart"
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
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.menu_account -> {
                startActivity(Intent(this, AccountActivity::class.java))
            }
        }
        return true
    }

    private fun init() {
        isCartEmpty()
        setupToolbar()
        cartAdapter = CartAdapter(this)
        cartAdapter.setOnAdapterListener(this)
        mList = dbHelper.getAllProduct()
        cartAdapter.setData(mList)


        recycler_view_cart.adapter = cartAdapter
        recycler_view_cart.layoutManager = LinearLayoutManager(this)

        updateText()

        button_place_order_cart.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }
    }

    private fun isCartEmpty() {
        if(dbHelper.isCartEmpty()) {
            main_layout.visibility = View.GONE
            empty_layout.visibility = View.VISIBLE

            //setupToolbar()
        }
    }

    fun updateText() {
        var orderSummary = dbHelper.calculation()
        price_checkout_display.text = "￥${orderSummary.ourPrice}"
        discount_checkout_display.text = "￥-${orderSummary.discount}"
        delivery_checkout_display.text = "￥${orderSummary.deliveryCharges}"
        total_checkout_display.text = "￥${orderSummary.totalAmount}"
    }

    override fun onButtonClicked(view: View, position: Int) {
        when(view.id){
            R.id.button_add_cart_adapter -> {
                updateText()
                updateCartCount()
            }
            R.id.button_minus_cart_adapter -> {
                isCartEmpty()
                updateText()
                updateCartCount()
            }
            R.id.button_delete_cart_adapter -> {
                isCartEmpty()
                updateText()
                updateCartCount()
            }
        }
    }
}