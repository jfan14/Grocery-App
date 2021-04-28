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
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.models.Product
import com.junfan.groceryapp.models.Product.Companion.PRODUCT_KEY
import com.junfan.groceryapp.session.SessionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.row_cart_adapter.*
import kotlinx.android.synthetic.main.row_product_adapter.*

class ProductDetailActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var mList: ArrayList<Product>
    //var counting: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        dbHelper = DBHelper(this)
        mList = dbHelper.getAllProduct()
        init()
    }

    private fun init() {
        setupToolbar()
        var product = intent.getSerializableExtra(PRODUCT_KEY) as Product
        Picasso
            .get()
            .load("${Config.IMAGE_URL + product.image}")
            .into(image_view_detail)

        text_view_name_detail.text = product.productName
        text_view_unit_detail.text = product.description
        text_view_price_detail.text = product.price.toString()

        button_add_to_cart_detail.setOnClickListener {
            dbHelper.addProduct(product)
            Toast.makeText(applicationContext, "Added to Cart", Toast.LENGTH_SHORT).show()
        }

        button_view_cart_detail.setOnClickListener {
            var intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title ="Details"
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
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        return true
    }
}