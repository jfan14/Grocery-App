package com.junfan.groceryapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.AdapterSubCategory
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.models.Category
import com.junfan.groceryapp.models.Category.Companion.KEY_CAT_ID
import com.junfan.groceryapp.models.CategoryResponse
import com.junfan.groceryapp.models.Product
import com.junfan.groceryapp.models.ProductResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product.view.*

class ProductFragment : Fragment() {

    var productList: ArrayList<Product> = ArrayList()
    var mContext: Context? = null
    lateinit var adapterSubCategory: AdapterSubCategory

    private var sub_id: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sub_id = it.getInt(KEY_CAT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_product, container, false)

        init(view)
        return view
    }

    private fun init(view: View) {
        getData()
        adapterSubCategory = AdapterSubCategory(activity!!)
        view.recycler_view_frag.adapter = adapterSubCategory
        view.recycler_view_frag.layoutManager = LinearLayoutManager(activity)
    }

    fun getData() {

        var requestQueue = Volley.newRequestQueue(activity)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductBySubId(sub_id!!),
            Response.Listener {
                Log.d("abcde", it.toString())
                var gson = Gson()
                var productResponse = gson.fromJson(it, ProductResponse::class.java)
                adapterSubCategory.setData(productResponse.data)
            },
            Response.ErrorListener {
                //Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                //Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(catId: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CAT_ID, catId)
                }
            }
    }
}