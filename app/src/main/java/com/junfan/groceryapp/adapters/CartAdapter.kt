package com.junfan.groceryapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junfan.groceryapp.R
import com.junfan.groceryapp.activities.CartActivity
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.database.DBHelper
import com.junfan.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class CartAdapter(var mContext: Context) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    var mList: ArrayList<Product> = ArrayList()
    var dbHelper: DBHelper = DBHelper(mContext)
    lateinit var cartActivity: CartActivity
    var listener: OnAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        cartActivity = CartActivity()
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product, position)

    }

    interface OnAdapterListener {
        fun onButtonClicked(view: View, position: Int)
    }

    fun setOnAdapterListener(onAdapterListener: OnAdapterListener) {
        listener = onAdapterListener
    }


    fun setData(list: ArrayList<Product>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product, position: Int) {

            itemView.text_view_name_cart.text = product.productName
            itemView.text_view_price_cart.text = product.price.toString()
            itemView.text_view_count_cart_adapter.text = product.quantity.toString()

            Picasso
                .get()
                .load("${Config.IMAGE_URL + product.image}")
                .into(itemView.image_view_cart)

            itemView.button_add_cart_adapter.setOnClickListener {
                dbHelper.incrementQuantity(product)
                mList = dbHelper.getAllProduct()
                setData(mList)
                listener?.onButtonClicked(it, position)
                itemView.text_view_count_cart_adapter.text = product.quantity.toString()

            }

            itemView.button_minus_cart_adapter.setOnClickListener {
                dbHelper.decrementQuantity(product)
                mList = dbHelper.getAllProduct()
                setData(mList)
                itemView.text_view_count_cart_adapter.text = product.quantity.toString()
                listener?.onButtonClicked(it, position)

            }

            itemView.button_delete_cart_adapter.setOnClickListener {
                dbHelper.deleteProduct(product)
                mList = dbHelper.getAllProduct()
                setData(mList)
                listener?.onButtonClicked(it, position)
            }
            //Log.d("abcd", "${product.quantity}")
        }
    }
}