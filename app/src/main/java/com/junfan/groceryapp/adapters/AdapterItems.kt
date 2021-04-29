package com.junfan.groceryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.models.Order
import com.junfan.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item_detail_adapter.view.*

class AdapterItems(var mContext: Context): RecyclerView.Adapter<AdapterItems.MyViewHolder>() {

    var mList: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_item_detail_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(list: ArrayList<Product>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            Picasso.get().load("${Config.Companion.IMAGE_URL+product.image}").into(itemView.image_view_item_detail)
            itemView.text_view_qty.text = "Qty ${product.quantity.toString()}"
            itemView.text_view_product_name_order_detail.text = product.productName
            itemView.text_view_amount_paid_order_detail.text = product.price.toString()
        }
    }
}