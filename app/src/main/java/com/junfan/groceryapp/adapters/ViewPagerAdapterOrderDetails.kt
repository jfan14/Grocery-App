package com.junfan.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.junfan.groceryapp.fragments.ItemFragment
import com.junfan.groceryapp.fragments.OrderSummaryFragment
import com.junfan.groceryapp.fragments.ProductFragment
import com.junfan.groceryapp.models.Order
import com.junfan.groceryapp.models.SubCategory

class ViewPagerAdapterOrderDetails(fm: FragmentManager): FragmentPagerAdapter(fm) {

    lateinit var mFragments1: Fragment

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> mFragments1
            else -> ItemFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    fun addFragment(order: Order) {
        mFragments1 = OrderSummaryFragment.newInstance(order)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Order Summary"
            else -> "Item"
        }
    }
}