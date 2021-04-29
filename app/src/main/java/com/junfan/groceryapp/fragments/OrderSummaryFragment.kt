package com.junfan.groceryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.models.Category
import com.junfan.groceryapp.models.Order
import com.junfan.groceryapp.models.OrderResponse
import com.junfan.groceryapp.session.SessionManager
import kotlinx.android.synthetic.main.fragment_order_summary.view.*
import java.text.ParseException
import java.text.SimpleDateFormat

class OrderSummaryFragment : Fragment() {

    private var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getSerializable("ORDER") as Order
            Log.d("jundebuger2", "${order.toString()}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        Log.d("jundebug", "${order.toString()}")
        var view = inflater.inflate(R.layout.fragment_order_summary, container, false)
        view.text_view_date_order_detail.text = convertMongoDate(order?.date!!)
        view.text_view_status_order_detail.text = "${order!!.payment.paymentMode} ${order!!.payment.paymentStatus}"
        view.text_view_bill_order_detail.text = order!!.user.email
        view.text_view_address_order_detail.text = "${order!!.shippingAddress.houseNo} ${order!!.shippingAddress.streetName}\n " +
                "${order!!.shippingAddress.city} ${order!!.shippingAddress.pincode}"
        view.subtotal_order_display.text = order!!.orderSummary.ourPrice.toString()
        view.discount_order_display.text = order!!.orderSummary.discount.toString()
        view.deliver_order_displayy.text = order!!.orderSummary.deliveryCharges.toString()
        view.order_amount_display.text = order!!.orderSummary.orderAmount.toString()
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(order: Order) =
            OrderSummaryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ORDER", order)
                    Log.d("jundebuger1", "${order.toString()}")
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