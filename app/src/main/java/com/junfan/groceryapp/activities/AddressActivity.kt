package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.AddressAdapter
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.models.Address
import com.junfan.groceryapp.models.AddressResponse
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_bar.*
import java.lang.reflect.Method

class AddressActivity : AppCompatActivity(), AddressAdapter.OnAdapterListener {

    var addressList: ArrayList<Address> = ArrayList()
    lateinit var addressAdapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        init()
    }

    private fun init() {
        setupToolbar()
        button_add_address.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
        getAddress()
        addressAdapter = AddressAdapter(this)
        addressAdapter.setOnAdapterListener(this)
        recycler_view_address.adapter = addressAdapter
        recycler_view_address.layoutManager = LinearLayoutManager(this)

        button_use_address.setOnClickListener {
            Toast.makeText(applicationContext, "Please choose an address first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Address"
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
            R.id.menu_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.menu_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> {
                sessionManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            android.R.id.home -> {
                startActivity(Intent(this, CartActivity::class.java))
            }
        }
        return true
    }

    private fun getAddress() {
        var sessionManager = SessionManager(this)
        var userId = sessionManager.getUserId()
        Log.d("abcd", "$userId")
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest (
            Request.Method.GET,
            Endpoints.getAddress(userId!!),
            Response.Listener {
                var gson = Gson()
                var addressResponse = gson.fromJson(it, AddressResponse::class.java)
                addressList = addressResponse.data
                addressAdapter.setData(addressList)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }

    fun deleteAddress(address: Address) {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.DELETE,
            Endpoints.deleteAddress(address._id),
            Response.Listener{
                Toast.makeText(applicationContext, "Address Deleted", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }

    override fun onButtonClicked(view: View, position: Int) {
        var address = addressList[position]
        when(view.id) {
            R.id.button_delete_address -> {
                deleteAddress(address)
            }
            R.id.button_edit_address -> {
                Log.d("abcde", "here")
                var intent = Intent(this, UpdateAddressActivity::class.java)
                intent.putExtra("ADDRESS", address)
                startActivity(intent)
            }
            R.id.radio_button -> {
                button_use_address.setOnClickListener {
                    var intent = Intent(this, PaymentActivity::class.java)
                    intent.putExtra("ADDRESS", address)
                    startActivity(intent)
                }
            }
        }
    }


}