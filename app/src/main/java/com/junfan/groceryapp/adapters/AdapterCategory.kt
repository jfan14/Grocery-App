package com.junfan.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junfan.groceryapp.R
import com.junfan.groceryapp.activities.SubCatActivity
import com.junfan.groceryapp.app.Config
import com.junfan.groceryapp.models.Category
import com.junfan.groceryapp.models.CategoryResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterCategory(var mContext: Context) :
    RecyclerView.Adapter<AdapterCategory.ViewHolder>() {

    var mList: ArrayList<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category)
    }

    fun setData(list: ArrayList<Category>){
        mList = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return mList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(category: Category) {
            itemView.text_view_category_name.text = category.catName
            Picasso
                .get()
                .load("${Config.IMAGE_URL+category.catImage}")
                .into(itemView.image_view_category)

            itemView.setOnClickListener {
                var intent = Intent(mContext, SubCatActivity::class.java)
                intent.putExtra("CAT_ID", category.catId)
                mContext.startActivity(intent)
            }
        }

    }

}