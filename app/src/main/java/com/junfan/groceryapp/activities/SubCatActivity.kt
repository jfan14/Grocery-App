package com.junfan.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junfan.groceryapp.R
import com.junfan.groceryapp.adapters.ViewPagerAdapter
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.models.SubCategory
import com.junfan.groceryapp.models.SubCategoryResponse
import kotlinx.android.synthetic.main.activity_sub_cat.*

class SubCatActivity : AppCompatActivity() {

    lateinit var viewPageAdapter: ViewPagerAdapter
    var subCategoryList: ArrayList<SubCategory> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_cat)

        init()
    }

    private fun init() {
        //Log.d("abc", "I am here")
        getData()
        viewPageAdapter = ViewPagerAdapter(supportFragmentManager)

    }

    private fun getData() {
        var catid = intent.getIntExtra("CAT_ID",0)
        Log.d("abc", "$catid")
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(catid),
            Response.Listener {
                var gson = Gson()
                var subCategoryResponse = gson.fromJson(it, SubCategoryResponse::class.java)
                subCategoryList = subCategoryResponse.data
                for(subCategory in subCategoryList) {
                    viewPageAdapter.addFragment(subCategory)
                }
                view_pager_sub.adapter = viewPageAdapter
                tab_layout_sub.setupWithViewPager(view_pager_sub)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)
    }
}