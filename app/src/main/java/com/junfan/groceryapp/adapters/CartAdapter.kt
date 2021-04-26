package com.junfan.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junfan.groceryapp.R
import com.junfan.groceryapp.activities.CartActivity
import com.junfan.groceryapp.activities.MainActivity
import com.junfan.groceryapp.activities.ProductDetailActivity
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class CartAdapter(var mContext: Context) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    var mList: ArrayList<Product> = ArrayList()
    var dbHelper: DBHelper = DBHelper(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }

    fun setData(list: ArrayList<Product>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product) {
            var number = 1
            itemView.text_view_name_cart.text = product.productName
            itemView.text_view_price_cart.text = product.price.toString()
            itemView.text_view_count_cart_adapter.text = number.toString()

            Picasso
                .get()
                .load("${Config.IMAGE_URL + product.image}")
                .into(itemView.image_view_cart)

            itemView.button_add_cart_adapter.setOnClickListener {
                number++
                itemView.text_view_count_cart_adapter.text = number.toString()
            }

            itemView.button_minus_cart_adapter.setOnClickListener {
                number--
                itemView.text_view_count_cart_adapter.text = number.toString()
                if(number == 0) {
                    dbHelper.deleteProduct(product)
                    mList = dbHelper.getAllProduct()
                    setData(mList)
                }
            }

            itemView.button_delete_cart_adapter.setOnClickListener {
                dbHelper.deleteProduct(product)
                mList = dbHelper.getAllProduct()
                setData(mList)
            }
        }
    }
}