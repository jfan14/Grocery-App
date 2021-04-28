package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        init()
    }

    private fun init() {
        var sessionManager = SessionManager(this)
        setupToolbar()

        button_add_address.setOnClickListener {
            var userId = sessionManager.getUserId()
            var city = edit_text_city.text.toString()
            var houseNo = edit_text_house_number.text.toString()
            var type = edit_text_type.text.toString()
            var streetName = edit_text_street_name.text.toString()
            var pincode = edit_text_pincode.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("userId", userId)
            jsonObject.put("city", city)
            jsonObject.put("houseNo", houseNo)
            jsonObject.put("type", type)
            jsonObject.put("streetName", streetName)
            jsonObject.put("pincode", pincode)

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.postAddress(),
                jsonObject,
                Response.Listener {
                    startActivity(Intent(this, AddressActivity::class.java))
                    Toast.makeText(applicationContext, "Address Added", Toast.LENGTH_SHORT).show()

                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(jsonRequest)
        }
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Categories"
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true;
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