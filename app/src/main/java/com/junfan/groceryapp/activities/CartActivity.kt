package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.CartAdapter
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var mList: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        dbHelper = DBHelper(this)
        mList = dbHelper.getAllProduct()

        init()
    }

    private fun init() {
        var cartAdapter = CartAdapter(this)
        cartAdapter.setData(mList)
        recycler_view_cart.adapter = cartAdapter
        recycler_view_cart.layoutManager = LinearLayoutManager(this)


        button_back_to_shopping.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}