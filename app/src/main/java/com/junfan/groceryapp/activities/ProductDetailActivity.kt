package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.models.Product
import com.junfan.groceryapp.models.Product.Companion.PRODUCT_KEY
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
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
        var product = intent.getSerializableExtra(PRODUCT_KEY) as Product

        Picasso
            .get()
            .load("${Config.IMAGE_URL + product.image}")
            .into(image_view_detail)

        text_view_name_detail.text = product.productName
        text_view_unit_detail.text = product.description
        text_view_price_detail.text = product.price.toString()

        button_add_to_cart_detail.setOnClickListener {
            /*var counter = 0
            for(products in mList) {
                Log.d("abcd", "${products.productName} and ${product.productName}")
                if(products.productName.equals(product.productName)) {
                    var count = text_view_count_cart_adapter.text.toString().toInt()
                    count++
                    text_view_count_cart_adapter.text = count.toString()
                }else{
                    counter++
                }
            }
            if(counter == mList.size) dbHelper.addProduct(product)
            Toast.makeText(applicationContext, "Added to Cart", Toast.LENGTH_SHORT).show()*/
            dbHelper.addProduct(product)
        }

        button_view_cart_detail.setOnClickListener {
            var intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
}