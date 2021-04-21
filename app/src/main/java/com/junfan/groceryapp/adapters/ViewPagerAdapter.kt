package com.junfan.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.junfan.groceryapp.fragments.ProductFragment
import com.junfan.groceryapp.models.SubCategory

class ViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    var mFragment: ArrayList<Fragment> = ArrayList()
    var mTitle: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragment[position]
    }

    override fun getCount(): Int {
        return mFragment.size
    }

    fun addFragment(subCategory: SubCategory) {
        mTitle.add(subCategory.subName)
        mFragment.add(ProductFragment.newInstance(subCategory.subId))
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }
}