package com.junfan.groceryapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junfan.groceryapp.R
import com.junfan.groceryapp.activities.ProductDetailActivity
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.models.Product
import com.junfan.groceryapp.models.Product.Companion.PRODUCT_KEY
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class AdapterSubCategory(var mContext: Context): RecyclerView.Adapter<AdapterSubCategory.ViewHolder>() {

    var mList: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_product_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(list: ArrayList<Product>){
        mList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.text_view_product_name.text = product.productName
            itemView.text_view_product_price.text = product.price.toString()
            itemView.text_view_product_mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_product_mrp.text = product.mrp.toString()
            Picasso
                .get()
                .load("${Config.IMAGE_URL+product.image}")
                .into(itemView.image_view_product)

            itemView.setOnClickListener {
                var intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.putExtra(PRODUCT_KEY, product)
                mContext.startActivity(intent)

            }
        }
    }
}