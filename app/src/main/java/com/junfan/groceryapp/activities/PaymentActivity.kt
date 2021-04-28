package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var option: String
    lateinit var address: Address

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
        orderJsonObject.put("orderAmount", orderSummary.orderAmount)
        orderJsonObject.put("discount", orderSummary.discount)
        orderJsonObject.put("ourPrice", orderSummary.ourPrice)
        orderJsonObject.put("deliveryCharges", orderSummary.deliveryCharges)
        orderJsonObject.put("totalAmount", orderSummary.totalAmount)

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

        var jsonArray: ArrayList<JSONObject> = ArrayList()
        var productJsonObject = JSONObject()
        for(i in 0 until productList.size) {
            var product = productList[i]
            //productJsonObject.put("_id", product._id)
            productJsonObject.put("quantity", product.quantity)
            productJsonObject.put("mrp", product.mrp)
            productJsonObject.put("productName", product.productName)
            productJsonObject.put("price", product.price)
            productJsonObject.put("image", product.image)
            jsonArray.add(productJsonObject)
        }

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
                Toast.makeText(applicationContext, "Successful Save order", Toast.LENGTH_SHORT).show()
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