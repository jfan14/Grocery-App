package com.junfan.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.AdapterCategory
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.models.Category
import com.junfan.groceryapp.models.CategoryResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var mList: ArrayList<Category> = ArrayList()
    lateinit var adapterCategory: AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun init() {
        //var fragmentAdapter = ViewPagerAdapter(supportFragmentManager)
        //view_pager_main.adapter = fragmentAdapter
        Picasso.get().load("https://pub-static.haozhaopian.net/assets/projects/pages/3d4d74c0-f53c-11e9-9514-3f31cfb386e6_12ddd30c-5a94-4145-9034-5c6b0a462df2_thumb.jpg")
            .into(image_view_banner)
        getData()
        adapterCategory = AdapterCategory(this)
        recycler_view.adapter = adapterCategory
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

    private fun getData() {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            Response.Listener {
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                adapterCategory.setData(categoryResponse.data)
                progress_bar.visibility = View.GONE
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)
    }
}