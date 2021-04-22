package com.junfan.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.models.Product
import com.junfan.groceryapp.models.Product.Companion.PRODUCT_KEY
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.row_product_adapter.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        init()
    }

    private fun init() {
        var product = intent.getSerializableExtra(PRODUCT_KEY) as Product

        Picasso
            .get()
            .load("${Config.IMAGE_URL+product.image}")
            .into(image_view_detail)

        text_view_name_detail.text = product.productName
        text_view_unit_detail.text = product.unit
        text_view_price_detail.text = product.price.toString()
    }
}