package com.junfan.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junfan.groceryapp.R
import com.junfan.groceryapp.activities.OrderDetailsActivity
import com.junfan.groceryapp.models.Order
import kotlinx.android.synthetic.main.row_order_adapter.view.*
import java.text.ParseException
import java.text.SimpleDateFormat

class OrderAdapter(var mContext: Context): RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    var mList: ArrayList<Order> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: OrderAdapter.MyViewHolder, position: Int) {
        var order = mList[position]
        holder.bind(order)
    }

    fun setData(list: ArrayList<Order>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(order: Order) {
            itemView.order_date_orders.text = convertMongoDate(order.date)
            var orderText = ""
            for(i in order.products) {
                orderText += "${i.quantity} x ${i.productName}\n"
            }
            itemView.order_items_orders.text = orderText
            var orderSummary = order.orderSummary
            if(orderSummary != null) itemView.order_total_orders.text = orderSummary.totalAmount.toString()

            itemView.setOnClickListener {
                var intent = Intent(mContext, OrderDetailsActivity::class.java)
                intent.putExtra("ORDER", order)
                Log.d("jundebug", "${order.toString()}")
                mContext.startActivity(intent)
            }
        }
    }

    fun convertMongoDate(date: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("MMM d, yyyy")
        try {
            val finalStr: String = outputFormat.format(inputFormat.parse(date))
            println(finalStr)
            return finalStr
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

}