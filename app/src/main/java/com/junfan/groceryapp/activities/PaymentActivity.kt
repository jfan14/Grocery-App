package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.models.Address
import com.junfan.groceryapp.models.Payment
import com.junfan.groceryapp.models.Product
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import org.json.JSONArray
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var option: String
    lateinit var address: Address
    private var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        init()
    }

    private fun init() {
        setupToolbar()
        setText()
        button_pay_online.setOnClickListener {
            option = "online"
            postSummaryOrder()
            startActivity(Intent(this, FinishActivity::class.java))
        }
        button_pay_cash.setOnClickListener {
            option = "cash"
            postSummaryOrder()
            startActivity(Intent(this, FinishActivity::class.java))
        }
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Payment"
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


    fun setText() {
        dbHelper = DBHelper(this)
        var orderSummary = dbHelper.calculation()

        text_view_amount_payment.text = "￥${(orderSummary.orderAmount).toString()}"
        text_view_saved_payment.text = "You saved ￥${(orderSummary.discount).toString()}"
        subtotal_payment_display.text = "￥${(orderSummary.ourPrice).toString()}"
        delivery_payment_display.text = "￥${(orderSummary.deliveryCharges).toString()}"
        discount_payment_display.text = "-￥${(orderSummary.discount).toString()}"
        amount_payment_display.text = "￥${(orderSummary.orderAmount).toString()}"
    }

    fun postSummaryOrder() {
        dbHelper = DBHelper(this)
        var productList = dbHelper.getAllProduct()
        var orderSummary = dbHelper.calculation()
        var sessionManager = SessionManager(this)
        address = intent.getSerializableExtra("ADDRESS") as Address

        var orderJsonObject = JSONObject()
        orderJsonObject.put("orderAmount", orderSummary.orderAmount.toInt())
        orderJsonObject.put("discount", orderSummary.discount.toInt())
        orderJsonObject.put("ourPrice", orderSummary.ourPrice.toInt())
        orderJsonObject.put("deliveryCharges", orderSummary.deliveryCharges.toInt())
        orderJsonObject.put("totalAmount", orderSummary.totalAmount.toInt())

        var paymentJsonObject = JSONObject()
        if(option.equals("cash")) {
            paymentJsonObject.put("paymentMode", "cash")
            paymentJsonObject.put("paymentStatus", "completed")
        }else {
            paymentJsonObject.put("paymentMode", "online")
            paymentJsonObject.put("paymentStatus", "completed")
        }

        var addressJsonObject = JSONObject()
        //addressJsonObject.put("type", address.type)
        addressJsonObject.put("houseNo", address.houseNo)
        addressJsonObject.put("city", address.city)
        addressJsonObject.put("streetName", address.streetName)
        addressJsonObject.put("pincode", address.pincode)

        var userJsonObject = JSONObject()
        //userJsonObject.put("_id", sessionManager.getUserId())
        userJsonObject.put("name", sessionManager.getName())
        userJsonObject.put("email", sessionManager.getEmail())
        userJsonObject.put("mobile", sessionManager.getMobile())

        var jsonArray = JSONArray()
        for(i in 0 until productList.size) {
            var product = productList[i]
            var productJsonObject = JSONObject()
            Log.d("abcde", "${product.productName}")
            //productJsonObject.put("_id", product._id)
            productJsonObject.put("quantity", product.quantity)
            productJsonObject.put("mrp", product.mrp?.toInt())
            productJsonObject.put("productName", product.productName)
            productJsonObject.put("price", product.price?.toInt())
            productJsonObject.put("image", product.image)

            jsonArray.put(productJsonObject)
        }
        Log.d("abcde", "${jsonArray}")

        var jsonObject = JSONObject()

        jsonObject.put("orderSummary", orderJsonObject)
        jsonObject.put("payment", paymentJsonObject)
        jsonObject.put("user", userJsonObject)
        jsonObject.put("shippingAddress", addressJsonObject)
        jsonObject.put("products", jsonArray)
        jsonObject.put("userId", sessionManager.getUserId().toString())

        var requestQueue = Volley.newRequestQueue(this)
        var jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.postOrder(),
            jsonObject,
            Response.Listener {
                dbHelper.deleteAllProduct()
                Toast.makeText(applicationContext, "Successful Save order", Toast.LENGTH_SHORT).show()
                Log.d("ae", "${Endpoints.postOrder().toString()}")
                Log.d("ae", "$jsonObject")
            },
            Response.ErrorListener {
                Log.d("ae", "${Endpoints.postOrder().toString()}")
                Log.d("ae", "$jsonObject")
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonRequest)
    }

}