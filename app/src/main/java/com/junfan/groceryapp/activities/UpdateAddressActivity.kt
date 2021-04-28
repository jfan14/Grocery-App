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
import com.junfan.groceryapp.models.Address
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_update_address.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class UpdateAddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_address)

        init()
    }

    private fun init() {
        setupToolbar()
        var address = intent.getSerializableExtra("ADDRESS") as Address
        editTextData(address)

        button_add_address_edit.setOnClickListener {
            updateAddress(address)
        }

    }

    fun editTextData(address: Address) {
        edit_text_city_edit.setText(address.city)
        edit_text_house_number_edit.setText(address.houseNo)
        edit_text_pincode_edit.setText((address.pincode).toString())
        edit_text_street_name_edit.setText(address.streetName)
        edit_text_type_edit.setText(address.type)
    }

    fun updateAddress(address: Address) {

        var houseNo = edit_text_house_number_edit.text.toString()
        var streetName = edit_text_street_name_edit.text.toString()
        var type = edit_text_type_edit.text.toString()
        var city = edit_text_city_edit.text.toString()
        var pincode = edit_text_pincode_edit.text.toString()

        var jsonObject = JSONObject()
        jsonObject.put("houseNo", houseNo)
        jsonObject.put("streetName", streetName)
        jsonObject.put("type", type)
        jsonObject.put("city", city)
        jsonObject.put("pincode", pincode)

        var requestQueue = Volley.newRequestQueue(this)
        var jsonRequest = JsonObjectRequest(
            Request.Method.PUT,
            Endpoints.updateAddress(address._id),
            jsonObject,
            Response.Listener{
                Toast.makeText(applicationContext, "Address Updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AddressActivity::class.java))
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonRequest)
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Update Address"
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
                startActivity(Intent(this, AddressActivity::class.java))
            }
        }
        return true
    }

}