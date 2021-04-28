package com.junfan.groceryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junfan.groceryapp.R
import com.junfan.groceryapp.activities.AddressActivity
import com.junfan.groceryapp.models.Address
import kotlinx.android.synthetic.main.row_address_adapter.view.*

class AddressAdapter(var mContext: Context): RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {

    var mList: ArrayList<Address> = ArrayList()
    var listener: OnAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_address_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(list: ArrayList<Address>) {
        mList = list
        notifyDataSetChanged()
    }

    fun deleteAddress(position: Int) {
        mList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var address = mList[position]
        holder.bind(address, position)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(address: Address, position: Int) {
            itemView.text_view_pincode_adapter.text = "Pincode: ${address.pincode.toString()}"
            itemView.text_view_city_adapter.text = "City: ${address.city}"
            itemView.text_view_house_number_adapter.text = "House Number: ${address.houseNo}"
            itemView.text_view_street_name_adapter.text = "Street Number: ${address.streetName}"
            itemView.text_view_type_adapter.text = "Type: ${address.type}"

            itemView.button_delete_address.setOnClickListener {
                notifyDataSetChanged()
                listener?.onButtonClicked(it, position)
                deleteAddress(position)
            }
            itemView.button_edit_address.setOnClickListener {
                notifyDataSetChanged()
                listener?.onButtonClicked(it, position)
            }
            itemView.radio_button.setOnClickListener {
                listener?.onButtonClicked(it, position)
            }
        }
    }

    interface OnAdapterListener {
        fun onButtonClicked(view: View, position: Int)
    }

    fun setOnAdapterListener(onAdapterListener: OnAdapterListener) {
        listener = onAdapterListener
    }
}