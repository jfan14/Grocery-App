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
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.ViewPagerAdapter
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.models.SubCategory
import com.junfan.groceryapp.models.SubCategoryResponse
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.activity_sub_cat.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

class SubCatActivity : AppCompatActivity() {

    lateinit var viewPageAdapter: ViewPagerAdapter
    var subCategoryList: ArrayList<SubCategory> = ArrayList()
    lateinit var dbHelper: DBHelper
    private var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_cat)
        dbHelper = DBHelper(this)
        updateCartCount()

        init()

    }


    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Sub Categories"
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

    private fun init() {
        //Log.d("abc", "I am here")
        setupToolbar()
        getData()
        viewPageAdapter = ViewPagerAdapter(supportFragmentManager)

        textViewCartCount?.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

    }

    private fun getData() {
        var catid = intent.getIntExtra("CAT_ID",0)
        Log.d("abc", "$catid")
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(catid),
            Response.Listener {
                var gson = Gson()
                var subCategoryResponse = gson.fromJson(it, SubCategoryResponse::class.java)
                subCategoryList = subCategoryResponse.data
                for(subCategory in subCategoryList) {
                    viewPageAdapter.addFragment(subCategory)
                }
                view_pager_sub.adapter = viewPageAdapter
                tab_layout_sub.setupWithViewPager(view_pager_sub)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)
    }

    fun updateCartCount() {
        var count = dbHelper.getQuantity()
        Log.d("acd2", "$count")
        if(count == 0) {
            textViewCartCount?.visibility = View.GONE
        }else{
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }
}