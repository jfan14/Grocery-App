package com.junfan.groceryapp.app

class Endpoints {

    companion object{

        private const val URL_CATEGORY ="category"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val URL_PRODUCT_BY_SUB_ID = "products/sub/"

        fun getCategory(): String{
            return "${Config.BASE_URL + URL_CATEGORY}"
        }

        fun getSubCategoryByCatId(catId: Int):String{
            return "${Config.BASE_URL + URL_SUB_CATEGORY + catId}"
        }

        fun getProductBySubId(subId: Int): String{
            return "${Config.BASE_URL + URL_PRODUCT_BY_SUB_ID + subId}"
        }

    }

}