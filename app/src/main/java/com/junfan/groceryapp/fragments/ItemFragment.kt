package com.junfan.groceryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.AdapterItems
import com.junfan.groceryapp.adapters.AdapterSubCategory
import com.junfan.groceryapp.models.Order
import com.junfan.groceryapp.models.Product
import kotlinx.android.synthetic.main.fragment_item.*
import kotlinx.android.synthetic.main.fragment_item.view.*
import kotlinx.android.synthetic.main.fragment_product.view.*

class ItemFragment : Fragment() {

    lateinit var adapterItems: AdapterItems

    private var products: ArrayList<Product>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            products = it.getSerializable("ORDER") as ArrayList<Product>
            Log.d("jundebuger2", "${products.toString()}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_item, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        adapterItems = AdapterItems(activity!!)
        adapterItems.setData(products!!)
        view.recycler_view_details.adapter = adapterItems
        view.recycler_view_details.layoutManager = LinearLayoutManager(activity)
    }

    companion object {
        @JvmStatic
        fun newInstance(products: ArrayList<Product>) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ORDER", products)
                    Log.d("jundebuger1", "${products.toString()}")
                }
            }
    }
}